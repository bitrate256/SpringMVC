package springMVC.vo;

import java.io.File;

public class ImageVO {
	/* mysql
	 * 멤버테이블 생성 후 이미지테이블 생성

	DROP TABLE tb_Member
	DROP TABLE tb_Image

	CREATE TABLE tb_Member(
	    memberSeq int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	    memberName varchar(50) NULL,
	    memberGrade varchar(1) NULL,
	    memberUseYN varchar(1) NULL,
	    regDate datetime NULL,
	);

	insert into tb_Member(memberName) values('홍길동');
	insert into tb_Member(memberName) values('셜록홈즈');
	insert into tb_Member(memberName) values('존왓슨');
	select * from tb_Member

	CREATE TABLE tb_Image(
		memberSeq int not null,
		fileName varchar(400) NOT NULL,
		uploadPath varchar(400) NOT NULL,
		uuid varchar(400) NOT NULL AUTO_INCREMENT PRIMARY KEY,
		primary key (uuid),
		FOREIGN KEY (memberSeq) REFERENCE tb_Member (memberSeq)
	);

	*/
		
	/* mssql
	 * 멤버테이블 생성 후 이미지테이블 생성

	DROP TABLE [dbo].[tb_Member]
	DROP TABLE [dbo].[tb_Image]

	CREATE TABLE [dbo].[tb_Member](
	    [memberSeq] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	    [memberName] [nvarchar](50) NULL,
	    [memberGrade] [nvarchar](1) NULL,
	    [memberUseYN] [nvarchar](1) NULL,
	    [regDate] [datetime] NULL,
	) ON [PRIMARY]

	insert into [dbo].[tb_Member] (memberName) values(N'홍길동')
	insert into [dbo].[tb_Member] (memberName) values(N'셜록홈즈')
	insert into [dbo].[tb_Member] (memberName) values(N'존왓슨')

	select * from [dbo].[tb_Member]

	CREATE TABLE [dbo].[tb_Image](
		memberSeq int not null,
		fileName nvarchar(400) not null,
		uploadPath nvarchar(400) not null,
		uuid nvarchar(400) not null,
		primary key (uuid),
		CONSTRAINT FK_memberSeq_member FOREIGN KEY (memberSeq) REFERENCES [dbo].[tb_Member](memberSeq)
	);
	*/
	
	// 멤버번호
	private int memberSeq;
	// 파일 이름
	private String fileName;
	// 경로 (uploadPath 를 받기 위해서는 File 타입이 되어야 함)
	private String uploadPath;
	// uuid
	private String uuid;
	
	public int getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(int memberSeq) {
		this.memberSeq = memberSeq;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
