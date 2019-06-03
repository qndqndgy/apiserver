package com.my.template.kafka.model;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * KafkaProduceModel.java
 * 현재 사용 안함.
 * @author 효민영♥
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class KafkaProduceModel {
	
	private String topic;
	private JSONObject data;

}
