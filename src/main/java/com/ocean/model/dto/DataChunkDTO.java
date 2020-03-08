/**
 *   Copyright(c) 2009-2015 GIS Department, NCI. All Rights Reserved.
 *
 *     THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF THE
 *                GIS DEPARTMENT, NCI.
 *
 *   The copyright notice above does not evidence any actual or intended
 *   publication of such source code.
 *
 *   Version 1.2 2009.06.18 07:54
 */
package com.ocean.model.dto;

import com.ocean.util.ByteStreamUtil;

public class DataChunkDTO
{
	public byte[] array = null;
	
	public int offset = 0;
	
	public int length = 0;
	
	public DataChunkDTO() {}
	
	public DataChunkDTO(byte[] bytes) {
		this.array = bytes;
		offset = 0;
		length = bytes.length;
	}
	
	public DataChunkDTO(byte[] array, int offset, int length) {
		this.array = array;
		this.offset = offset;
		this.length = length;
	}
	
	public static DataChunkDTO forByteStream(ByteStreamUtil bs) {
		int nleft = bs.length() - bs.read_pos();
		if (nleft > 0) {
			return new DataChunkDTO(bs.array(), bs.read_pos(), bs.length());
		} else {
			return new DataChunkDTO();
		}
	}
}
