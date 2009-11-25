/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cassandra.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.cassandra.db.ColumnFamily;
import org.apache.cassandra.db.ReadResponse;
import org.apache.cassandra.db.Row;
import org.apache.cassandra.db.RowMutation;
import org.apache.cassandra.db.RowMutationMessage;
import org.apache.cassandra.io.DataInputBuffer;
import java.net.InetAddress;
import org.apache.cassandra.net.Message;
import org.apache.cassandra.utils.LogUtil;
import org.apache.cassandra.utils.FBUtilities;

import org.apache.log4j.Logger;


/**
 * This class is used by all read functions and is called by the Quorum 
 * when at least a few of the servers (few is specified in Quorum)
 * have sent the response . The resolve function then schedules read repair 
 * and resolution of read data from the various servers.
 */
public class ReadResponseResolver implements IResponseResolver<Row>
{
	private static Logger logger_ = Logger.getLogger(ReadResponseResolver.class);
    private final String table;

    public ReadResponseResolver(String table)
    {
        this.table = table;
    }

    /*
      * This method for resolving read data should look at the timestamps of each
      * of the columns that are read and should pick up columns with the latest
      * timestamp. For those columns where the timestamp is not the latest a
      * repair request should be scheduled.
      *
      */
	public Row resolve(List<Message> responses) throws DigestMismatchException, IOException
    {
        long startTime = System.currentTimeMillis();
		Row resolved = null;
		List<Row> rowList = new ArrayList<Row>();
		List<InetAddress> endPoints = new ArrayList<InetAddress>();
		String key = null;
		byte[] digest = new byte[0];
		boolean isDigestQuery = false;
        
        /*
		 * Populate the list of rows from each of the messages
		 * Check to see if there is a digest query. If a digest 
         * query exists then we need to compare the digest with 
         * the digest of the data that is received.
        */
        DataInputBuffer bufIn = new DataInputBuffer();
		for (Message response : responses)
		{					            
            byte[] body = response.getMessageBody();
            bufIn.reset(body, body.length);
            ReadResponse result = ReadResponse.serializer().deserialize(bufIn);
            if (result.isDigestQuery())
            {
                digest = result.digest();
                isDigestQuery = true;
            }
            else
            {
                rowList.add(result.row());
                endPoints.add(response.getFrom());
                key = result.row().key;
            }
        }
		// If there was a digest query compare it with all the data digests 
		// If there is a mismatch then throw an exception so that read repair can happen.
        if (isDigestQuery)
        {
            for (Row row : rowList)
            {
                if (!Arrays.equals(row.digest(), digest))
                {
                    /* Wrap the key as the context in this exception */
                    String s = String.format("Mismatch for key %s (%s vs %s)", row.key, FBUtilities.bytesToHex(row.digest()), FBUtilities.bytesToHex(digest));
                    throw new DigestMismatchException(s);
                }
            }
        }

        /* If the rowList is empty then we had some exception above. */
        if (rowList.size() == 0)
        {
            return resolved;
        }

        /* Now calculate the resolved row */
        resolved = new Row(key, rowList.get(0).cf);
        for (Row other : rowList.subList(1, rowList.size()))
        {
            resolved.resolve(other);
        }

        // At this point we have the return row;
        // Now we need to calculate the difference so that we can schedule read repairs
        for (int i = 0; i < rowList.size(); i++)
        {
            // since retRow is the resolved row it can be used as the super set
            ColumnFamily diffCf = rowList.get(i).cf.diff(resolved.cf);
            if (diffCf == null) // no repair needs to happen
                continue;
            // create the row mutation message based on the diff and schedule a read repair
            RowMutation rowMutation = new RowMutation(table, key);
            rowMutation.add(diffCf);
            RowMutationMessage rowMutationMessage = new RowMutationMessage(rowMutation);
            ReadRepairManager.instance().schedule(endPoints.get(i), rowMutationMessage);
        }
        if (logger_.isDebugEnabled())
            logger_.debug("resolve: " + (System.currentTimeMillis() - startTime) + " ms.");
		return resolved;
	}

	public boolean isDataPresent(List<Message> responses)
	{
        boolean isDataPresent = false;
        for (Message response : responses)
        {
            byte[] body = response.getMessageBody();
            DataInputBuffer bufIn = new DataInputBuffer();
            bufIn.reset(body, body.length);
            try
            {
                ReadResponse result = ReadResponse.serializer().deserialize(bufIn);
                if (!result.isDigestQuery())
                {
                    isDataPresent = true;
                }
                bufIn.close();
            }
            catch (IOException ex)
            {
                logger_.info(LogUtil.throwableToString(ex));
            }
        }
        return isDataPresent;
    }
}
