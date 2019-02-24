package com.util;

import java.util.Comparator;

import com.vo.FileVO;

public class SizeComparator implements Comparator<FileVO>{

	@Override
	public int compare(FileVO obj1, FileVO obj2) {
		return (obj2.getStrFileSizeType().compareTo(obj1.getStrFileSizeType())==0)?obj2.getIntegerFileSize().compareTo(obj1.getIntegerFileSize()):obj2.getStrFileSizeType().compareTo(obj1.getStrFileSizeType());
		//return obj2.getIntegerFileSize().compareTo(obj1.getIntegerFileSize());
	}
}
