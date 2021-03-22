package com.web.bigdata.model.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.bigdata.model.UserDto;
import com.web.bigdata.model.ReviewDto;

@Mapper
public interface UserMapper {

	// UserController
	/** 회원 목록을 List로 반환 */
	public List<UserDto> getAllUser() throws SQLException;

	/** 회원의 권한 반환 */
	public String getRole(String email) throws SQLException;

	/** 인자로 UserDto를 생성 */
	public void join(UserDto dto) throws Exception;

	/** 인자로 받은 email의 중복 여부 */
	public String emailCheck(String email);

	/** 인자로 받은 name의 중복 여부 */
	public String nameCheck(String name);

	/** 인자로 받은 email의 비밀번호를 반환 */
	public UserDto findUserInfo(String email) throws SQLException;

	/** 인자로 받은 UserDto의 비밀번호를 수정하고, 성공 여부를 반환 */
	public boolean updatePwd(UserDto userDto) throws SQLException;

	/** 인자로 받은 UserDto를 삭제하고, 성공 여부를 반환 */
	public boolean delete(String email) throws SQLException;

	/** 인자로 받은 UserDto로 프로필을 설정 */
	public void saveImg(UserDto userDto) throws IOException;

	/** 인자로 받은 email의 프로필 사진을 삭제 */
	public void deleteImg(String email);

	// LoginController

	/** id, pwd와 일치하는 UserDto를 반환 */
	public UserDto login(UserDto dto) throws SQLException;

	/** 인자로 받은 email의 비밀번호를 반환 */
	public String findPwd(String email);

	/** 게시글 리스트 */
	public List<ReviewDto> getPostList(String email) throws SQLException;

}
