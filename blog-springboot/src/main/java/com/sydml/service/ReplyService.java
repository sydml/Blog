package com.sydml.service;

import com.sydml.dao.BlogDao;
import com.sydml.dao.DiscussDao;
import com.sydml.dao.ReplyDao;
import com.sydml.dao.UserDao;
import com.sydml.model.pojo.Blog;
import com.sydml.model.pojo.Discuss;
import com.sydml.model.pojo.Reply;
import com.sydml.model.pojo.User;
import com.sydml.utils.DateUtil;
import com.sydml.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private DiscussDao discussDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 保存回复
     *
     * @param discussId
     * @param replyBody
     * @param rootId    可为null
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveReply(Integer discussId, String replyBody, Integer rootId) {
        User user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        Reply reply = new Reply();
        Discuss discuss = discussDao.findDiscussById(discussId);
        if (discuss == null) {
            throw new RuntimeException("评论不存在");
        }

        reply.setDiscuss(discuss);
        reply.setUser(user);
        reply.setBody(replyBody);
        reply.setTime(dateUtil.getCurrentDate());
        Reply rootReply = new Reply();
        rootReply.setId(rootId);
        reply.setReply(rootReply);
        replyDao.saveReply(reply);

        //更新博文评论数
        Blog blog = blogDao.findBlogById(discuss.getBlog().getId());
        blog.setDiscussCount(blog.getDiscussCount() + 1);
        blogDao.updateBlog(blog);

    }

    /**
     * 删除回复
     *
     * @param replyId
     */
    public void deleteReply(Integer replyId) {
        User user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        Reply reply = replyDao.findReplyById(replyId);
        if (reply == null) {
            throw new RuntimeException("回复不存在");
        }

        if (!user.getId().equals(reply.getUser().getId())) {
            throw new RuntimeException("无权删除");
        }

        //删除回复
        replyDao.deleteReplyById(replyId);

        Discuss discuss = discussDao.findDiscussById(reply.getDiscuss().getId());
        //更新博文评论数
        Blog blog = blogDao.findBlogById(discuss.getBlog().getId());
        blog.setDiscussCount(blog.getDiscussCount() - 1);
        blogDao.updateBlog(blog);

    }

    /**
     * 管理员删除回复
     *
     * @param replyId
     */
    public void adminDeleteReply(Integer replyId) {
        Reply reply = replyDao.findReplyById(replyId);
        if (reply == null) {
            throw new RuntimeException("回复不存在");
        }
        //删除回复
        replyDao.deleteReplyById(replyId);

        Discuss discuss = discussDao.findDiscussById(reply.getDiscuss().getId());
        //更新博文评论数
        Blog blog = blogDao.findBlogById(discuss.getBlog().getId());
        blog.setDiscussCount(blog.getDiscussCount() - 1);
        blogDao.updateBlog(blog);
    }
}
