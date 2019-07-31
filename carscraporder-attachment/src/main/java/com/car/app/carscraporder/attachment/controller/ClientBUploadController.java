package com.car.app.carscraporder.attachment.controller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.car.app.carscraporder.attachment.Exception.AttachmentException;
import com.car.app.carscraporder.attachment.bean.PicUploadResult;
import com.car.app.carscraporder.attachment.bean.UploadVO;
import com.car.app.carscraporder.attachment.service.UploadService;
import com.car.app.carscraporder.attachment.service.impl.PropertieService;
import com.car.app.carscraporder.vo.AttachmentVO;
import com.car.app.common.bean.ResultBean;


@Controller
@RequestMapping(value="/upload")
public class ClientBUploadController {

	@Autowired
	private UploadService  uploadService;

	@Autowired
	private PropertieService propertieService;


	@RequestMapping(value="/attachmet",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<List<PicUploadResult>> addOrUpdateResource(
			@RequestParam("files") CommonsMultipartFile[] files) throws Exception {
		List<PicUploadResult> resultList = new ArrayList<PicUploadResult>();

		//判断上传文件是否为空
		if(files!=null){
			for(CommonsMultipartFile file: files){
				//校验附件是否为空或者
				PicUploadResult result = validFile(file);
				if(result.getError()==0){
					// 文件新路径
					String filePath = getFilePath(file.getOriginalFilename());
					System.out.println("没进入方法内的filePath"+filePath);
					File newFile = new File(filePath);
					result.setUrl(getUrl(filePath));
					result.setOriginalFilename(file.getOriginalFilename());
					//上传到本地硬盘
					file.transferTo(newFile);
					Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
					perms.add(PosixFilePermission.OWNER_READ);//设置所有者的读取权限
					perms.add(PosixFilePermission.OWNER_WRITE);//设置所有者的写权限
					perms.add(PosixFilePermission.OWNER_EXECUTE);//设置所有者的执行权限
					perms.add(PosixFilePermission.GROUP_READ);//设置组的读取权限
					perms.add(PosixFilePermission.GROUP_EXECUTE);//设置组的读取权限
					perms.add(PosixFilePermission.GROUP_WRITE);//设置组的读取权限
					perms.add(PosixFilePermission.OTHERS_READ);//设置其他的读取权限
					perms.add(PosixFilePermission.OTHERS_EXECUTE);//设置其他的读取权限
					perms.add(PosixFilePermission.OTHERS_WRITE);
					try {
						Path path = Paths.get(filePath);
						Path path2 = Paths.get(getUrl(filePath));
						Files.setPosixFilePermissions(path, perms);
						Files.setPosixFilePermissions(path2, perms);
					} catch (Exception e) {

					}
					//保存到数据库
					AttachmentVO  data = new AttachmentVO();
					data.setOriginalName(file.getOriginalFilename());
					data.setStoragePath(getUrl(filePath));
					Integer id =  uploadService.saveAttachmentBuniessData(data);
					result.setId(id);
					if(id==null){
						result.setOriginalFilename(file.getOriginalFilename());
						result.setError(3);
						result.setMessage("保存数据库错误");
					}
				}
				resultList.add(result);
			}
		}else{
			throw new AttachmentException("附件为空");

		}
		return new ResultBean<List<PicUploadResult>>(resultList);

	}



	@RequestMapping(value="/carattachment/{buniessid}",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<List<PicUploadResult>> addOrUpdateResource(
			@PathVariable("buniessid") String buniessid,
			@RequestParam(value="businessType",required=true) String businessType,
			@RequestParam(value="pictureType",required=true) String pictureType,
			@RequestParam(value="picturePecialType",required=false) Integer picturePecialType,
			@RequestParam("files") CommonsMultipartFile[] files) throws Exception {

		UploadVO vo = new UploadVO();
		vo.setBuniessid(buniessid);
		vo.setBusinessType(businessType);
		vo.setPictureType(pictureType);
		vo.setPicturePecialType(picturePecialType);

		List<PicUploadResult> resultList = new ArrayList<PicUploadResult>();

		//判断上传文件是否为空
		if(files!=null){
			for(CommonsMultipartFile file: files){
				//校验附件是否为空或者
				PicUploadResult result = validFile(file);
				if(result.getError()==0){
					// 文件新路径
					String filePath = getFilePath(file.getOriginalFilename());
					File newFile = new File(filePath);
					result.setUrl(getUrl(filePath));
					//上传到本地硬盘
					file.transferTo(newFile);
					//保存到数据库
					AttachmentVO  data = new AttachmentVO();
					data.setBuniessid(vo.getBuniessid());
					data.setBusinessType(vo.getBusinessType());
					data.setPicturePecialType(vo.getPicturePecialType());
					data.setPictureType(vo.getPictureType());
					data.setOriginalName(file.getOriginalFilename());
					data.setStoragePath(getUrl(filePath));
					Integer id =  uploadService.saveCarAttachmentBuniessData(vo.getBuniessid(),data);
					result.setId(id);
					if(id==null){
						result.setError(3);
						result.setMessage("保存数据库错误");
					}
				}
				resultList.add(result);
			}
		}else{
			throw new AttachmentException("附件为空");
		}
		return new ResultBean(resultList);
	}


	private String getUrl(String relativePath){
		String picUrl = StringUtils.replace(
				StringUtils.substringAfter(relativePath, propertieService.REPOSITORY_PATH), "\\", "/");
		picUrl =  propertieService.PICURL+picUrl;
		return picUrl;
	}

	private String getVisitPath(String url){
		String picUrl = StringUtils.replace(
				StringUtils.substringAfter(url, propertieService.REPOSITORY_PATH), "\\", "/");
		String visitUrl =  propertieService.PICURL+url;
		return visitUrl;
	}

	private PicUploadResult validFile(CommonsMultipartFile file){
		PicUploadResult picUploadResult = new PicUploadResult();
		if(file.getSize()>0){
			if(file.getSize()>10485760){//上传文件不能大于10M
				String message = "上传文件不能大于5M";
				picUploadResult.setOriginalFilename(file.getOriginalFilename());
				picUploadResult.setError(2);
				picUploadResult.setMessage(message);
			}else{
				picUploadResult.setOriginalFilename(file.getOriginalFilename());
				picUploadResult.setError(0);

			}
		}else{
			picUploadResult.setError(1);
			picUploadResult.setMessage("附件不能为空");

		}

		return picUploadResult;

	}


	public static void main(String[] args) {
		String ss ="333"+"/";
//		System.out.println(StringUtils.indexOf(ss, "."));
		System.out.println(ss);
	}

	private String getFilePath(String sourceFileName) {
		if(StringUtils.indexOf(sourceFileName, ".")<0){
			sourceFileName =sourceFileName+".jpg";
		}

		String baseFolder = propertieService.REPOSITORY_PATH + File.separator + "images";
		Date nowDate = new Date();
		// yyyy/MM/dd
		String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy")
				+ File.separator + new DateTime(nowDate).toString("MM") + File.separator
				+ new DateTime(nowDate).toString("dd");
		File file = new File(fileFolder);
		if (!file.isDirectory()) {
			// 如果目录不存在，则创建目录
			file.mkdirs();
		}
		// 生成新的文件名
		String fileName = new DateTime(nowDate).toString("yyyyMMddHHmmssSSSS")
				+ RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
		//设置权限
		Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
		perms.add(PosixFilePermission.OWNER_READ);//设置所有者的读取权限
		perms.add(PosixFilePermission.OWNER_WRITE);//设置所有者的写权限
		perms.add(PosixFilePermission.OWNER_EXECUTE);//设置所有者的执行权限
		perms.add(PosixFilePermission.GROUP_READ);//设置组的读取权限
		perms.add(PosixFilePermission.GROUP_EXECUTE);//设置组的读取权限
		perms.add(PosixFilePermission.GROUP_WRITE);//设置组的读取权限
		perms.add(PosixFilePermission.OTHERS_READ);//设置其他的读取权限
		perms.add(PosixFilePermission.OTHERS_EXECUTE);//设置其他的读取权限
		perms.add(PosixFilePermission.OTHERS_WRITE);
		try {
			//设置文件和文件夹的权限
			Path pathParent = Paths.get(file.getParentFile().getAbsolutePath());
			Path pathDest = Paths.get(file.getAbsolutePath());
			Path pathSon = Paths.get(file.getAbsolutePath()+"/"+fileName);

			System.out.println("img_path_b:"+file.getAbsolutePath()+"/"); //得到日期目录
			System.out.println("img_path_c:"+file.getAbsolutePath()+"/"+fileName); //得到文件目录
			Files.setPosixFilePermissions(pathParent, perms);//修改文件夹路径的权限
			Files.setPosixFilePermissions(pathDest, perms);//修改图片文件的权限
			Files.setPosixFilePermissions(pathSon, perms);//修改图片文件的权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileFolder + File.separator + fileName;
	}


	public static String getExt(MultipartFile file){
		return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1).toLowerCase();
	}

	private String getFileKB(long byteFile){
		if(byteFile==0)
			return "0KB";
		long kb=1024;
		return ""+byteFile/kb+"KB";
	}
	private String getFileMB(long byteFile){
		if(byteFile==0)
			return "0MB";
		long mb=1024*1024;
		return ""+byteFile/mb+"MB";
	}




	public Integer getNullIsZaro(Integer OriginalNumber){
		if(OriginalNumber==null){
			return null;
		}else{
			if(OriginalNumber==0){
				return null;
			}else{
				return OriginalNumber;
			}
		}

	}
}
