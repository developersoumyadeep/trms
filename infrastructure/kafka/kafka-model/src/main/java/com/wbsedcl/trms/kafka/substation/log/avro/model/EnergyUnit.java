/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.wbsedcl.trms.kafka.substation.log.avro.model;
@org.apache.avro.specific.AvroGenerated
public enum EnergyUnit implements org.apache.avro.generic.GenericEnumSymbol<EnergyUnit> {
  WH, KWH, MWH  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"EnergyUnit\",\"namespace\":\"com.wbsedcl.trms.kafka.substation.log.avro.model\",\"symbols\":[\"WH\",\"KWH\",\"MWH\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}