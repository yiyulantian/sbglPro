package cn.edu.hrbeu.pojo;


/**
 * @CopyRright (c)2011-2012:   <哈尔滨工程大学>                         
 * @Project:                   <等级保护安全测评平台 >                                          
 * @Comments:  <此类是操作记录单的POJO类>                                          
 * @JDK version used:      <JDK1.6>                                                      
 * @Author：        <杨熙>                
 * @Create Date：  <2011-06-13>
 * @Modified By：  
 * @Modified Date:                                
 * @Why & What is modified
 * @Version:   <v2.1>
 */
public class Operation{


    private String OperationID;

    private String OperationName;
    
    private String OperationDescription;    
    
    private String ParentID;
    
    private boolean Flag;
    
    private String Icon;
    
    private boolean Check;
	private String URL;
    
    private boolean Disply;
    
    private String Remark;


	public boolean isCheck() {
		return Check;
	}

	public void setCheck(boolean check) {
		Check = check;
	}

	public String getOperationID() {
		return OperationID;
	}

	public void setOperationID(String operationID) {
		OperationID = operationID;
	}

	public String getOperationName() {
		return OperationName;
	}

	public void setOperationName(String operationName) {
		OperationName = operationName;
	}

	public String getOperationDescription() {
		return OperationDescription;
	}

	public void setOperationDescription(String operationDescription) {
		OperationDescription = operationDescription;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public boolean isFlag() {
		return Flag;
	}

	public void setFlag(boolean flag) {
		Flag = flag;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public boolean isDisply() {
		return Disply;
	}

	public void setDisply(boolean disply) {
		Disply = disply;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}


    


   
}


