/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.cassandra.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import org.apache.log4j.Logger;

import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.protocol.*;

public class Column implements TBase, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("Column");
  private static final TField NAME_FIELD_DESC = new TField("name", TType.STRING, (short)1);
  private static final TField VALUE_FIELD_DESC = new TField("value", TType.STRING, (short)2);
  private static final TField TIMESTAMP_FIELD_DESC = new TField("timestamp", TType.I64, (short)3);

  public byte[] name;
  public static final int NAME = 1;
  public byte[] value;
  public static final int VALUE = 2;
  public long timestamp;
  public static final int TIMESTAMP = 3;

  private final Isset __isset = new Isset();
  private static final class Isset implements java.io.Serializable {
    public boolean timestamp = false;
  }

  public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    put(NAME, new FieldMetaData("name", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    put(VALUE, new FieldMetaData("value", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    put(TIMESTAMP, new FieldMetaData("timestamp", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I64)));
  }});

  static {
    FieldMetaData.addStructMetaDataMap(Column.class, metaDataMap);
  }

  public Column() {
  }

  public Column(
    byte[] name,
    byte[] value,
    long timestamp)
  {
    this();
    this.name = name;
    this.value = value;
    this.timestamp = timestamp;
    this.__isset.timestamp = true;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Column(Column other) {
    if (other.isSetName()) {
      this.name = new byte[other.name.length];
      System.arraycopy(other.name, 0, name, 0, other.name.length);
    }
    if (other.isSetValue()) {
      this.value = new byte[other.value.length];
      System.arraycopy(other.value, 0, value, 0, other.value.length);
    }
    __isset.timestamp = other.__isset.timestamp;
    this.timestamp = other.timestamp;
  }

  @Override
  public Column clone() {
    return new Column(this);
  }

  public byte[] getName() {
    return this.name;
  }

  public Column setName(byte[] name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  // Returns true if field name is set (has been asigned a value) and false otherwise
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public byte[] getValue() {
    return this.value;
  }

  public Column setValue(byte[] value) {
    this.value = value;
    return this;
  }

  public void unsetValue() {
    this.value = null;
  }

  // Returns true if field value is set (has been asigned a value) and false otherwise
  public boolean isSetValue() {
    return this.value != null;
  }

  public void setValueIsSet(boolean value) {
    if (!value) {
      this.value = null;
    }
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public Column setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    this.__isset.timestamp = true;
    return this;
  }

  public void unsetTimestamp() {
    this.__isset.timestamp = false;
  }

  // Returns true if field timestamp is set (has been asigned a value) and false otherwise
  public boolean isSetTimestamp() {
    return this.__isset.timestamp;
  }

  public void setTimestampIsSet(boolean value) {
    this.__isset.timestamp = value;
  }

  public void setFieldValue(int fieldID, Object value) {
    switch (fieldID) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((byte[])value);
      }
      break;

    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((byte[])value);
      }
      break;

    case TIMESTAMP:
      if (value == null) {
        unsetTimestamp();
      } else {
        setTimestamp((Long)value);
      }
      break;

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  public Object getFieldValue(int fieldID) {
    switch (fieldID) {
    case NAME:
      return getName();

    case VALUE:
      return getValue();

    case TIMESTAMP:
      return new Long(getTimestamp());

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
  public boolean isSet(int fieldID) {
    switch (fieldID) {
    case NAME:
      return isSetName();
    case VALUE:
      return isSetValue();
    case TIMESTAMP:
      return isSetTimestamp();
    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Column)
      return this.equals((Column)that);
    return false;
  }

  public boolean equals(Column that) {
    if (that == null)
      return false;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!java.util.Arrays.equals(this.name, that.name))
        return false;
    }

    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (!java.util.Arrays.equals(this.value, that.value))
        return false;
    }

    boolean this_present_timestamp = true;
    boolean that_present_timestamp = true;
    if (this_present_timestamp || that_present_timestamp) {
      if (!(this_present_timestamp && that_present_timestamp))
        return false;
      if (this.timestamp != that.timestamp)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id)
      {
        case NAME:
          if (field.type == TType.STRING) {
            this.name = iprot.readBinary();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case VALUE:
          if (field.type == TType.STRING) {
            this.value = iprot.readBinary();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case TIMESTAMP:
          if (field.type == TType.I64) {
            this.timestamp = iprot.readI64();
            this.__isset.timestamp = true;
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
          break;
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();


    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.name != null) {
      oprot.writeFieldBegin(NAME_FIELD_DESC);
      oprot.writeBinary(this.name);
      oprot.writeFieldEnd();
    }
    if (this.value != null) {
      oprot.writeFieldBegin(VALUE_FIELD_DESC);
      oprot.writeBinary(this.value);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(TIMESTAMP_FIELD_DESC);
    oprot.writeI64(this.timestamp);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Column(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
        int __name_size = Math.min(this.name.length, 128);
        for (int i = 0; i < __name_size; i++) {
          if (i != 0) sb.append(" ");
          sb.append(Integer.toHexString(this.name[i]).length() > 1 ? Integer.toHexString(this.name[i]).substring(Integer.toHexString(this.name[i]).length() - 2).toUpperCase() : "0" + Integer.toHexString(this.name[i]).toUpperCase());
        }
        if (this.name.length > 128) sb.append(" ...");
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("value:");
    if (this.value == null) {
      sb.append("null");
    } else {
        int __value_size = Math.min(this.value.length, 128);
        for (int i = 0; i < __value_size; i++) {
          if (i != 0) sb.append(" ");
          sb.append(Integer.toHexString(this.value[i]).length() > 1 ? Integer.toHexString(this.value[i]).substring(Integer.toHexString(this.value[i]).length() - 2).toUpperCase() : "0" + Integer.toHexString(this.value[i]).toUpperCase());
        }
        if (this.value.length > 128) sb.append(" ...");
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("timestamp:");
    sb.append(this.timestamp);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check that fields of type enum have valid values
  }

}

