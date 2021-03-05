package com.finalproject.BankApplication.service;

import com.finalproject.BankApplication.model.Assessment;
import com.finalproject.BankApplication.model.AssessmentStatus;
import com.finalproject.BankApplication.model.AssessmentType;
import com.finalproject.BankApplication.model.Decision;
import com.finalproject.BankApplication.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AssessmentService {

    @Autowired
    AssessmentRepository assessmentRepository;

    public void saveNew(Assessment assessment){
        assessmentRepository.save(assessment);
    }

    public int findLastId(){
        return assessmentRepository.findFirstByOrderByIdDesc().getId();
    }

    public Assessment findById(int Id){
        return assessmentRepository.findById(Id).get();
    }

//  Find Assessment

    public List<Assessment> findAll(){
        List<Assessment> assessmentList = new ArrayList<>();
        return assessmentRepository.findAll();}

    public List<Assessment> findOpen(){
        List<Assessment> WIPRequestList = findWIP();
        List<Assessment> pendingRequestList = findPending();
        List<Assessment> openedRequestList = Stream.of(WIPRequestList, pendingRequestList)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
        return openedRequestList;}

    public List<Assessment> findPending(){
        List<Assessment> pendingRequestList = new ArrayList<>();
        pendingRequestList = assessmentRepository.findAssessmentByStatus(AssessmentStatus.PENDING);
        return pendingRequestList;}

    public List<Assessment> findWIP(){
        List<Assessment> WIPRequestList = new ArrayList<>();
        WIPRequestList = assessmentRepository.findAssessmentByStatus(AssessmentStatus.IN_PROGRESS);
        return WIPRequestList;}

    public List<Assessment> findDone(){
        List<Assessment> accountRequestList = new ArrayList<>();
        accountRequestList = assessmentRepository.findAssessmentByStatus(AssessmentStatus.DONE);
        return accountRequestList;}


//  Find by type

    public List<Assessment> findOpenRequest(AssessmentType type){
        List<Assessment> openedRequestList = new ArrayList<>();
        openedRequestList = findOpen().
                stream().filter(a->a.getType().equals(type)).
                collect(Collectors.toList());
        return openedRequestList;}

    public List<Assessment> findPendingRequest(AssessmentType type){
        List<Assessment> pendingRequestList = new ArrayList<>();
        pendingRequestList = findPending().
                stream().filter(a->a.getType().equals(type)).
                collect(Collectors.toList());
        return pendingRequestList;}

    public List<Assessment> findWIPRequest(AssessmentType type){
        List<Assessment> WIPRequestList = new ArrayList<>();
        WIPRequestList = findWIP().
                stream().filter(a->a.getType().equals(type)).
                collect(Collectors.toList());
        return WIPRequestList;}

    public List<Assessment> findDoneRequest(AssessmentType type){
        List<Assessment> doneAccountRequestList = new ArrayList<>();
        doneAccountRequestList = findDone().
                stream().filter(a->a.getType().equals(type)).
                collect(Collectors.toList());
        return doneAccountRequestList;}

//  Find Account Request

    public List<Assessment> findAccountRequest(){return assessmentRepository.findAssessmentByType(AssessmentType.ACCOUNT);}

    public List<Assessment> findAccountRequestOpen(){return findOpenRequest(AssessmentType.ACCOUNT);}

    public List<Assessment> findAccountRequestPending(){return findPendingRequest(AssessmentType.ACCOUNT);}

    public List<Assessment> findAccountRequestWIP(){return findWIPRequest(AssessmentType.ACCOUNT);}

    public List<Assessment> findAccountRequestDone(){return findDoneRequest(AssessmentType.ACCOUNT);}

//  Find Loan Request

    public List<Assessment> findLoanRequest(){return assessmentRepository.findAssessmentByType(AssessmentType.LOAN);}

    public List<Assessment> findLoanRequestOpen(){return findOpenRequest(AssessmentType.LOAN);}
    public List<Assessment> findLoanRequestPending(){return findPendingRequest(AssessmentType.LOAN);}

    public List<Assessment> findLoanRequestWIP(){return findWIPRequest(AssessmentType.LOAN);}

    public List<Assessment> findLoanRequestDone(){return findDoneRequest(AssessmentType.LOAN);}

    public Map<String, Integer> statistics(){
        Map<String, Integer> statistics = new HashMap<>();
        int totalRequests= findAll()!=null?findAll().size():0;

        int totalOpen= findOpen()!=null?findOpen().size():0;
        int totalWIP= findWIP()!=null?findWIP().size():0;
        int totalPending= findPending()!=null?findPending().size():0;
        int totalCompleted= findDone()!=null?findDone().size():0;
        //account
        int totalAccountRequests= findAccountRequest()!=null?findAccountRequest().size():0;
        int totalAccountOpen= findAccountRequestOpen()!=null?findAccountRequestOpen().size():0;
        int totalAccountWIP= findAccountRequestWIP()!=null?findAccountRequestWIP().size():0;
        int totalAccountPending= findAccountRequestPending()!=null?findAccountRequestPending().size():0;
        int totalAccountCompleted=findAccountRequestDone()!=null?findAccountRequestDone().size():0;
        //loan
        int totalLoanRequests= findLoanRequest()!=null?findLoanRequest().size():0;
        int totalLoanOpen= findLoanRequestOpen()!=null?findLoanRequestOpen().size():0;
        int totalLoanWIP= findLoanRequestWIP()!=null?findLoanRequestWIP().size():0;
        int totalLoanPending= findLoanRequestPending()!=null?findLoanRequestPending().size():0;
        int totalLoanCompleted=findLoanRequestDone()!=null?findLoanRequestDone().size():0;

        statistics.put("totalRequests", totalRequests);

        statistics.put("totalOpen", totalOpen);
        statistics.put("totalWIP", totalWIP);
        statistics.put("totalPending", totalPending);
        statistics.put("totalCompleted", totalCompleted);
        //account
        statistics.put("totalAccountRequests", totalAccountRequests);
        statistics.put("totalAccountOpen", totalAccountOpen);
        statistics.put("totalAccountWIP", totalAccountWIP);
        statistics.put("totalAccountPending", totalAccountPending);
        statistics.put("totalAccountCompleted",totalAccountCompleted);
        //loan
        statistics.put("totalLoanRequests", totalLoanRequests);
        statistics.put("totalLoanOpen", totalLoanOpen);
        statistics.put("totalLoanWIP", totalLoanWIP);
        statistics.put("totalLoanPending", totalLoanPending);
        statistics.put("totalLoanCompleted",totalLoanCompleted);

        return statistics;}

    public void submit(int id ){
        assessmentRepository.changeStatus(AssessmentStatus.PENDING,id);
    }
    public void start(int id ){
        assessmentRepository.changeStatus(AssessmentStatus.IN_PROGRESS,id);
    }
    public void done(int id ){
        assessmentRepository.changeStatus(AssessmentStatus.DONE,id);
    }

    public void accountType(int id ){
        assessmentRepository.changeType(AssessmentType.ACCOUNT,id);
    }
    public void loanType(int id ){
        assessmentRepository.changeType(AssessmentType.LOAN,id);
    }

    public void approved(int id ){
        assessmentRepository.changeDecision(Decision.APPROVED,id);
    }
    public void rejected(int id ){
        assessmentRepository.changeDecision(Decision.REJECTED,id);
    }




}
