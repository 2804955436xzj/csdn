package com.xzj.csdn.service;

import com.xzj.csdn.dto.CommentDTO;
import com.xzj.csdn.dto.QuestionDTO;
import com.xzj.csdn.enums.CommentTypeEnum;
import com.xzj.csdn.exception.CustomizeErrorCode;
import com.xzj.csdn.exception.CustomizeException;
import com.xzj.csdn.mapper.CommentMapper;
import com.xzj.csdn.mapper.QuestionExtMapper;
import com.xzj.csdn.mapper.QuestionMapper;
import com.xzj.csdn.mapper.UserMapper;
import com.xzj.csdn.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xzj
 * @date 2019/7/27-8:58
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentMapper commentMapper;

    public List<QuestionDTO> list() {
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExample(questionExample);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(question.getUserId());
            List<User> users = userMapper.selectByExample(example);
//            User user = userMapper(question.getUserId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;

    }

    public List<QuestionDTO> listById(String accountId) {

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(accountId);
        List<Question> questionList = questionMapper.selectByExample(questionExample);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(question.getUserId());
            List<User> users = userMapper.selectByExample(example);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public QuestionDTO findByid(long id) {
        Question question = questionMapper.selectByPrimaryKey(id);

        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(question.getUserId());
        List<User> users = userMapper.selectByExample(example);
        questionDTO.setUser(users.get(0));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建问题
            question.setGmtModified(System.currentTimeMillis());
            question.setGmtCreate(question.getGmtModified());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insertSelective(question);
        }else {
            //更新问题
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();

            example.createCriteria().andIdEqualTo(question.getId());

            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }
    //添加评论数
    public void incView(long id) {

        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
    //查询相关问题
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);
        List<Question> questionList = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questionList.stream().map(q -> {

            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());


        return questionDTOS;
    }

    public List<Question> list(String search) {
        if(StringUtils.isNotBlank(search)){
            String[] searchs = StringUtils.split(search, " ");
            search = Arrays.stream(searchs).collect(Collectors.joining("|"));
        }

        QuestionExample example = new QuestionExample();

        List<Question> questionList = questionExtMapper.selectSearch(search);
        return questionList;
    }
}
