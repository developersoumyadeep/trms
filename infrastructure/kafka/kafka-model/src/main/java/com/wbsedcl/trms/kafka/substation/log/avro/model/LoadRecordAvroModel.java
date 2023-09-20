/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.wbsedcl.trms.kafka.substation.log.avro.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class LoadRecordAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 2260513254349826292L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"LoadRecordAvroModel\",\"namespace\":\"com.wbsedcl.trms.kafka.substation.log.avro.model\",\"fields\":[{\"name\":\"loadRecordId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"sagaId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"feederId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"substationOfficeId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"recordedBy\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"date\",\"type\":{\"type\":\"int\",\"logicalType\":\"date\"}},{\"name\":\"time\",\"type\":{\"type\":\"long\",\"logicalType\":\"time-millis\"}},{\"name\":\"load\",\"type\":\"double\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.DateConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.UUIDConversion());
  }

  private static final BinaryMessageEncoder<LoadRecordAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<LoadRecordAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<LoadRecordAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<LoadRecordAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<LoadRecordAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this LoadRecordAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a LoadRecordAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a LoadRecordAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static LoadRecordAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.UUID loadRecordId;
  private java.util.UUID sagaId;
  private java.lang.String feederId;
  private java.lang.String substationOfficeId;
  private java.lang.String recordedBy;
  private java.time.LocalDate date;
  private long time;
  private double load;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public LoadRecordAvroModel() {}

  /**
   * All-args constructor.
   * @param loadRecordId The new value for loadRecordId
   * @param sagaId The new value for sagaId
   * @param feederId The new value for feederId
   * @param substationOfficeId The new value for substationOfficeId
   * @param recordedBy The new value for recordedBy
   * @param date The new value for date
   * @param time The new value for time
   * @param load The new value for load
   */
  public LoadRecordAvroModel(java.util.UUID loadRecordId, java.util.UUID sagaId, java.lang.String feederId, java.lang.String substationOfficeId, java.lang.String recordedBy, java.time.LocalDate date, java.lang.Long time, java.lang.Double load) {
    this.loadRecordId = loadRecordId;
    this.sagaId = sagaId;
    this.feederId = feederId;
    this.substationOfficeId = substationOfficeId;
    this.recordedBy = recordedBy;
    this.date = date;
    this.time = time;
    this.load = load;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return loadRecordId;
    case 1: return sagaId;
    case 2: return feederId;
    case 3: return substationOfficeId;
    case 4: return recordedBy;
    case 5: return date;
    case 6: return time;
    case 7: return load;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      new org.apache.avro.Conversions.UUIDConversion(),
      new org.apache.avro.Conversions.UUIDConversion(),
      null,
      null,
      null,
      new org.apache.avro.data.TimeConversions.DateConversion(),
      null,
      null,
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: loadRecordId = (java.util.UUID)value$; break;
    case 1: sagaId = (java.util.UUID)value$; break;
    case 2: feederId = value$ != null ? value$.toString() : null; break;
    case 3: substationOfficeId = value$ != null ? value$.toString() : null; break;
    case 4: recordedBy = value$ != null ? value$.toString() : null; break;
    case 5: date = (java.time.LocalDate)value$; break;
    case 6: time = (java.lang.Long)value$; break;
    case 7: load = (java.lang.Double)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'loadRecordId' field.
   * @return The value of the 'loadRecordId' field.
   */
  public java.util.UUID getLoadRecordId() {
    return loadRecordId;
  }


  /**
   * Sets the value of the 'loadRecordId' field.
   * @param value the value to set.
   */
  public void setLoadRecordId(java.util.UUID value) {
    this.loadRecordId = value;
  }

  /**
   * Gets the value of the 'sagaId' field.
   * @return The value of the 'sagaId' field.
   */
  public java.util.UUID getSagaId() {
    return sagaId;
  }


  /**
   * Sets the value of the 'sagaId' field.
   * @param value the value to set.
   */
  public void setSagaId(java.util.UUID value) {
    this.sagaId = value;
  }

  /**
   * Gets the value of the 'feederId' field.
   * @return The value of the 'feederId' field.
   */
  public java.lang.String getFeederId() {
    return feederId;
  }


  /**
   * Sets the value of the 'feederId' field.
   * @param value the value to set.
   */
  public void setFeederId(java.lang.String value) {
    this.feederId = value;
  }

  /**
   * Gets the value of the 'substationOfficeId' field.
   * @return The value of the 'substationOfficeId' field.
   */
  public java.lang.String getSubstationOfficeId() {
    return substationOfficeId;
  }


  /**
   * Sets the value of the 'substationOfficeId' field.
   * @param value the value to set.
   */
  public void setSubstationOfficeId(java.lang.String value) {
    this.substationOfficeId = value;
  }

  /**
   * Gets the value of the 'recordedBy' field.
   * @return The value of the 'recordedBy' field.
   */
  public java.lang.String getRecordedBy() {
    return recordedBy;
  }


  /**
   * Sets the value of the 'recordedBy' field.
   * @param value the value to set.
   */
  public void setRecordedBy(java.lang.String value) {
    this.recordedBy = value;
  }

  /**
   * Gets the value of the 'date' field.
   * @return The value of the 'date' field.
   */
  public java.time.LocalDate getDate() {
    return date;
  }


  /**
   * Sets the value of the 'date' field.
   * @param value the value to set.
   */
  public void setDate(java.time.LocalDate value) {
    this.date = value;
  }

  /**
   * Gets the value of the 'time' field.
   * @return The value of the 'time' field.
   */
  public long getTime() {
    return time;
  }


  /**
   * Sets the value of the 'time' field.
   * @param value the value to set.
   */
  public void setTime(long value) {
    this.time = value;
  }

  /**
   * Gets the value of the 'load' field.
   * @return The value of the 'load' field.
   */
  public double getLoad() {
    return load;
  }


  /**
   * Sets the value of the 'load' field.
   * @param value the value to set.
   */
  public void setLoad(double value) {
    this.load = value;
  }

  /**
   * Creates a new LoadRecordAvroModel RecordBuilder.
   * @return A new LoadRecordAvroModel RecordBuilder
   */
  public static com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder newBuilder() {
    return new com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder();
  }

  /**
   * Creates a new LoadRecordAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new LoadRecordAvroModel RecordBuilder
   */
  public static com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder newBuilder(com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder other) {
    if (other == null) {
      return new com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder();
    } else {
      return new com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new LoadRecordAvroModel RecordBuilder by copying an existing LoadRecordAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new LoadRecordAvroModel RecordBuilder
   */
  public static com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder newBuilder(com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel other) {
    if (other == null) {
      return new com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder();
    } else {
      return new com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for LoadRecordAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<LoadRecordAvroModel>
    implements org.apache.avro.data.RecordBuilder<LoadRecordAvroModel> {

    private java.util.UUID loadRecordId;
    private java.util.UUID sagaId;
    private java.lang.String feederId;
    private java.lang.String substationOfficeId;
    private java.lang.String recordedBy;
    private java.time.LocalDate date;
    private long time;
    private double load;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.loadRecordId)) {
        this.loadRecordId = data().deepCopy(fields()[0].schema(), other.loadRecordId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.sagaId)) {
        this.sagaId = data().deepCopy(fields()[1].schema(), other.sagaId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.feederId)) {
        this.feederId = data().deepCopy(fields()[2].schema(), other.feederId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.substationOfficeId)) {
        this.substationOfficeId = data().deepCopy(fields()[3].schema(), other.substationOfficeId);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.recordedBy)) {
        this.recordedBy = data().deepCopy(fields()[4].schema(), other.recordedBy);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.date)) {
        this.date = data().deepCopy(fields()[5].schema(), other.date);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.time)) {
        this.time = data().deepCopy(fields()[6].schema(), other.time);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.load)) {
        this.load = data().deepCopy(fields()[7].schema(), other.load);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
    }

    /**
     * Creates a Builder by copying an existing LoadRecordAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.loadRecordId)) {
        this.loadRecordId = data().deepCopy(fields()[0].schema(), other.loadRecordId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.sagaId)) {
        this.sagaId = data().deepCopy(fields()[1].schema(), other.sagaId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.feederId)) {
        this.feederId = data().deepCopy(fields()[2].schema(), other.feederId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.substationOfficeId)) {
        this.substationOfficeId = data().deepCopy(fields()[3].schema(), other.substationOfficeId);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.recordedBy)) {
        this.recordedBy = data().deepCopy(fields()[4].schema(), other.recordedBy);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.date)) {
        this.date = data().deepCopy(fields()[5].schema(), other.date);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.time)) {
        this.time = data().deepCopy(fields()[6].schema(), other.time);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.load)) {
        this.load = data().deepCopy(fields()[7].schema(), other.load);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'loadRecordId' field.
      * @return The value.
      */
    public java.util.UUID getLoadRecordId() {
      return loadRecordId;
    }


    /**
      * Sets the value of the 'loadRecordId' field.
      * @param value The value of 'loadRecordId'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setLoadRecordId(java.util.UUID value) {
      validate(fields()[0], value);
      this.loadRecordId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'loadRecordId' field has been set.
      * @return True if the 'loadRecordId' field has been set, false otherwise.
      */
    public boolean hasLoadRecordId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'loadRecordId' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearLoadRecordId() {
      loadRecordId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'sagaId' field.
      * @return The value.
      */
    public java.util.UUID getSagaId() {
      return sagaId;
    }


    /**
      * Sets the value of the 'sagaId' field.
      * @param value The value of 'sagaId'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setSagaId(java.util.UUID value) {
      validate(fields()[1], value);
      this.sagaId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'sagaId' field has been set.
      * @return True if the 'sagaId' field has been set, false otherwise.
      */
    public boolean hasSagaId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'sagaId' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearSagaId() {
      sagaId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'feederId' field.
      * @return The value.
      */
    public java.lang.String getFeederId() {
      return feederId;
    }


    /**
      * Sets the value of the 'feederId' field.
      * @param value The value of 'feederId'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setFeederId(java.lang.String value) {
      validate(fields()[2], value);
      this.feederId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'feederId' field has been set.
      * @return True if the 'feederId' field has been set, false otherwise.
      */
    public boolean hasFeederId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'feederId' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearFeederId() {
      feederId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'substationOfficeId' field.
      * @return The value.
      */
    public java.lang.String getSubstationOfficeId() {
      return substationOfficeId;
    }


    /**
      * Sets the value of the 'substationOfficeId' field.
      * @param value The value of 'substationOfficeId'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setSubstationOfficeId(java.lang.String value) {
      validate(fields()[3], value);
      this.substationOfficeId = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'substationOfficeId' field has been set.
      * @return True if the 'substationOfficeId' field has been set, false otherwise.
      */
    public boolean hasSubstationOfficeId() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'substationOfficeId' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearSubstationOfficeId() {
      substationOfficeId = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'recordedBy' field.
      * @return The value.
      */
    public java.lang.String getRecordedBy() {
      return recordedBy;
    }


    /**
      * Sets the value of the 'recordedBy' field.
      * @param value The value of 'recordedBy'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setRecordedBy(java.lang.String value) {
      validate(fields()[4], value);
      this.recordedBy = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'recordedBy' field has been set.
      * @return True if the 'recordedBy' field has been set, false otherwise.
      */
    public boolean hasRecordedBy() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'recordedBy' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearRecordedBy() {
      recordedBy = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'date' field.
      * @return The value.
      */
    public java.time.LocalDate getDate() {
      return date;
    }


    /**
      * Sets the value of the 'date' field.
      * @param value The value of 'date'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setDate(java.time.LocalDate value) {
      validate(fields()[5], value);
      this.date = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'date' field has been set.
      * @return True if the 'date' field has been set, false otherwise.
      */
    public boolean hasDate() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'date' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearDate() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'time' field.
      * @return The value.
      */
    public long getTime() {
      return time;
    }


    /**
      * Sets the value of the 'time' field.
      * @param value The value of 'time'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setTime(long value) {
      validate(fields()[6], value);
      this.time = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'time' field has been set.
      * @return True if the 'time' field has been set, false otherwise.
      */
    public boolean hasTime() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'time' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearTime() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'load' field.
      * @return The value.
      */
    public double getLoad() {
      return load;
    }


    /**
      * Sets the value of the 'load' field.
      * @param value The value of 'load'.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder setLoad(double value) {
      validate(fields()[7], value);
      this.load = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'load' field has been set.
      * @return True if the 'load' field has been set, false otherwise.
      */
    public boolean hasLoad() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'load' field.
      * @return This builder.
      */
    public com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel.Builder clearLoad() {
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public LoadRecordAvroModel build() {
      try {
        LoadRecordAvroModel record = new LoadRecordAvroModel();
        record.loadRecordId = fieldSetFlags()[0] ? this.loadRecordId : (java.util.UUID) defaultValue(fields()[0]);
        record.sagaId = fieldSetFlags()[1] ? this.sagaId : (java.util.UUID) defaultValue(fields()[1]);
        record.feederId = fieldSetFlags()[2] ? this.feederId : (java.lang.String) defaultValue(fields()[2]);
        record.substationOfficeId = fieldSetFlags()[3] ? this.substationOfficeId : (java.lang.String) defaultValue(fields()[3]);
        record.recordedBy = fieldSetFlags()[4] ? this.recordedBy : (java.lang.String) defaultValue(fields()[4]);
        record.date = fieldSetFlags()[5] ? this.date : (java.time.LocalDate) defaultValue(fields()[5]);
        record.time = fieldSetFlags()[6] ? this.time : (java.lang.Long) defaultValue(fields()[6]);
        record.load = fieldSetFlags()[7] ? this.load : (java.lang.Double) defaultValue(fields()[7]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<LoadRecordAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<LoadRecordAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<LoadRecordAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<LoadRecordAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}









