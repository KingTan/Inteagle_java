package com.inteagle.utils.struct;

import com.inteagle.modal.base.BaseEntity;
import com.inteagle.modal.struct.IdInfoStruct;

import struct.StructClass;
import struct.StructField;

/**
 * 
 * @ClassName: CameraStruct
 * @Description: TODO(相机结构体)
 * @author IVAn
 * @date 2019年8月1日下午3:15:22
 *
 */
@StructClass
public class CameraStruct extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	private IdInfoStruct idInfoStruct;

	@StructField(order = 1)
	private float rotation;

	@StructField(order = 2)
	private float translation;

	@StructField(order = 3)
	private float distortion;

	@StructField(order = 4)
	private float k;

	public IdInfoStruct getIdInfoStruct() {
		return idInfoStruct;
	}

	public void setIdInfoStruct(IdInfoStruct idInfoStruct) {
		this.idInfoStruct = idInfoStruct;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getTranslation() {
		return translation;
	}

	public void setTranslation(float translation) {
		this.translation = translation;
	}

	public float getDistortion() {
		return distortion;
	}

	public void setDistortion(float distortion) {
		this.distortion = distortion;
	}

	public float getK() {
		return k;
	}

	public void setK(float k) {
		this.k = k;
	}

}
