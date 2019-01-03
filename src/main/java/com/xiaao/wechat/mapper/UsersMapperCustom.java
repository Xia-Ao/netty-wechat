package com.xiaao.wechat.mapper;


import com.xiaao.wechat.pojo.Users;
import com.xiaao.wechat.pojo.vo.FriendRequestVo;
import com.xiaao.wechat.pojo.vo.MyFriendsVo;
import com.xiaao.wechat.utils.MyMapper;

import java.util.List;


public interface UsersMapperCustom extends MyMapper<Users> {

    public List<FriendRequestVo> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVo> queryMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String > msgIdList);

}