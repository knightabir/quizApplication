package com.quiz.service.impl;

import com.quiz.dto.QuestionDto;
import com.quiz.helper.CurrentUser;
import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private ExamServiceImpl examService;

    @Autowired
    private ExamCategoryServiceImpl categoryService;
    @Autowired
    private TestServiceImpl testService;

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question getById(String id) throws Exception {
        return questionRepository.findById(id).orElseThrow(()-> new Exception("Question not found with this id."));
    }

@Override
public Question update(String id, QuestionDto question) throws Exception {
    Question existingQuestion = getById(id);

    if (question.getQuestion() != null && !question.getQuestion().isEmpty()) {
        existingQuestion.setQuestion(question.getQuestion());
    }

    if (question.getCorrectAnswer() != null && !question.getCorrectAnswer().isEmpty()) {
        existingQuestion.setCorrectAnswer(question.getCorrectAnswer());
    }

    if (question.getExamCategory() != null && !question.getExamCategory().isEmpty()) {
        existingQuestion.setExamCategory(categoryService.getExamCategoryById(question.getQuestion()));
    }

    if (question.getOptionA() != null && !question.getOptionA().isEmpty()) {
        existingQuestion.setOptionA(question.getOptionA());
    }

    if (question.getOptionB() != null && !question.getOptionB().isEmpty()) {
        existingQuestion.setOptionB(question.getOptionB());
    }

    if (question.getOptionC() != null && !question.getOptionC().isEmpty()) {
        existingQuestion.setOptionC(question.getOptionC());
    }

    if (question.getOptionD() != null && !question.getOptionD().isEmpty()) {
        existingQuestion.setOptionD(question.getOptionD());
    }

    if (question.getOptionE() != null && !question.getOptionE().isEmpty()) {
        existingQuestion.setOptionE(question.getOptionE());
    }

    if (question.getTest() != null && !question.getTest().isEmpty()) {
        existingQuestion.setTest(testService.getById(question.getTest()));
    }
    if (question.getExam() != null){
        existingQuestion.setExam(examService.getById(question.getExam()));
    }
    existingQuestion.setUpdatedAt(System.currentTimeMillis());
    existingQuestion.setUpdatedBy(currentUser.getCurrentUserId());

    return questionRepository.save(existingQuestion);
}

    @Override
    public void save(QuestionDto question) throws Exception {
        if (question.getQuestion() == null || question.getQuestion().trim().isEmpty() || question.getQuestion().trim().isBlank()){
            throw new Exception("Question should not blank.");
        } else if (question.getCorrectAnswer() == null || question.getCorrectAnswer().trim().isEmpty() || question.getCorrectAnswer().trim().isBlank()){
            throw new Exception("Correct answer should not blank");
        } else if (question.getOptionA() == null || question.getOptionA().strip().isBlank() || question.getOptionA().trim().isEmpty()) {
            throw new Exception("First option is not blank.");
        } else if (question.getOptionB() == null || question.getOptionB().strip().isBlank() || question.getOptionB().trim().isEmpty()) {
            throw new Exception("Second option is not blank.");
        }
        Question data = new Question();
        data.setQuestion(question.getQuestion());
        data.setOptionA(question.getOptionA());
        data.setOptionB(question.getOptionB());
        data.setOptionC(question.getOptionC());
        data.setOptionD(question.getOptionD());
        data.setOptionE(question.getOptionE());
        data.setCorrectAnswer(question.getCorrectAnswer());
        data.setCreatedAt(System.currentTimeMillis());
        data.setCreatedBy(currentUser.getCurrentUserId());
        data.setExam(examService.getById(question.getExam()));
        data.setExamCategory(categoryService.getExamCategoryById(question.getExamCategory()));
        data.setTest(testService.getById(question.getId()));
        questionRepository.save(data);
    }

    @Override
    public void deleteById(String id) throws Exception {
        Question existingQuestion = getById(id);
        if (existingQuestion != null){
            questionRepository.deleteById(id);
        }
    }

}
