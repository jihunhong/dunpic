package com.oz.dunpic.Entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TryRecord {

    @Id
    @GeneratedValue
    int id;

    String name;

    int try_count;

    BigInteger gold;

}