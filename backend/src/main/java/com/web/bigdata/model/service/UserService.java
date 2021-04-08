package com.web.bigdata.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.web.bigdata.model.UserDto;
import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.StoreDto;

public interface UserService {

	// UserController
	/** 회원 목록을 List로 반환 */
	public List<UserDto> getAllUser() throws Exception;

	/** 회원의 권한 반환 */
	public String getRole(String email) throws Exception;

	/** 인자로 UserDto를 생성 */
	public void join(UserDto dto) throws Exception;

	/** 인자로 받은 email의 중복 여부 */
	public boolean emailCheck(String email);

	/** 인자로 받은 name의 중복 여부 */
	public boolean nameCheck(String name);

	/** 인자로 받은 email에 해당하는 유저 정보를 반환 */
	public UserDto findUserInfo(String email) throws Exception;

	public boolean updateNickname(UserDto userDto) throws Exception;

	/** 인자로 받은 email의 User를 삭제하고, 성공 여부를 반환 */
	public boolean delete(String email) throws Exception;

	/** 인자로 받은 UserDto로 프로필을 설정 */
	public void saveImg(UserDto userDto) throws IOException, SQLException;

	/** 인자로 받은 email의 프로필 사진을 삭제 */
	public void deleteImg(String email) throws Exception;

	/** 리뷰 리스트 */
	public List<ReviewDto> getReviewList(String email) throws SQLException;

	/** 북마크 리스트 */
	public List<BookmarkDto> getBookmarkList(int id_user) throws SQLException;

	/** email 로 id_user 가져오기 */
	public Integer getIdUser(String email) throws Exception;

	/** id_user 로 nickname 가져오기 */
	public String getNickName(int id_user) throws SQLException;

	// LoginController

	/** id, pwd와 일치하는 UserDto를 반환 */
	public UserDto login(UserDto dto) throws Exception;

}
