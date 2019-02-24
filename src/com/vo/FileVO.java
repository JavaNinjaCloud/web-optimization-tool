package com.vo;

import java.io.File;

public class FileVO {

	private File file;
	private String strFileName;
	private String strFileSizeType;
	private int intFileSize;
	private boolean blUsed;
	private int intLineNo;
	private String strCode;
	private String str404File;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getStrFileName() {
		return strFileName;
	}
	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	public boolean isBlUsed() {
		return blUsed;
	}
	public void setBlUsed(boolean blUsed) {
		this.blUsed = blUsed;
	}
	public int getIntFileSize() {
		return intFileSize;
	}
	public Integer getIntegerFileSize() {
		return intFileSize;
	}
	public void setIntFileSize(int intFileSize) {
		this.intFileSize = intFileSize;
	}
	public String getStrFileSizeType() {
		return strFileSizeType;
	}
	public void setStrFileSizeType(String strFileSizeType) {
		this.strFileSizeType = strFileSizeType;
	}
	public int getIntLineNo() {
		return intLineNo;
	}
	public void setIntLineNo(int intLineNo) {
		this.intLineNo = intLineNo;
	}
	public String getStrCode() {
		return strCode;
	}
	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}
	public String getStr404File() {
		return str404File;
	}
	public void setStr404File(String str404File) {
		this.str404File = str404File;
	}
	
}
