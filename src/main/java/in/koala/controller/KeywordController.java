package in.koala.controller;

import in.koala.annotation.Auth;
import in.koala.annotation.Xss;
import in.koala.domain.Keyword;
import in.koala.domain.Notice;
import in.koala.domain.response.CustomBody;
import in.koala.service.KeywordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;
    
    @Xss
    @Auth
    @ApiOperation(value ="키워드 조회" , notes = "사용자가 지정한 키워드를 조회한다." , authorizations = @Authorization(value = "Bearer +accessToken"))
    @GetMapping(value = "/keyword")
    public ResponseEntity<List<Keyword>> myKeywordList(){
        return new ResponseEntity(CustomBody.of(keywordService.myKeywordList(), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @Auth
    @ApiOperation(value ="키워드 추가" , notes = "사용자가 지정한 키워드를 등록한다." , authorizations = @Authorization(value = "Bearer +accessToken"))
    @PostMapping(value = "/keyword")
    public ResponseEntity registerKeyword(@RequestBody Keyword keyword) throws Exception {
        if(keywordService.registerKeyword(keyword))
            return new ResponseEntity(CustomBody.of("키워드 추가에 성공하였습니다.",  HttpStatus.OK), HttpStatus.OK);
        else
            return new ResponseEntity(CustomBody.of("키워드 추가에 실패하였습니다.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 삭제", notes = "사용자가 지정한 키워드를 삭제한다.", authorizations = @Authorization(value = "Bearer +accessToken"))
    @PatchMapping(value = "/keyword")
    public ResponseEntity deleteKeyword(@RequestParam(name = "keyword-name") String keywordName) throws Exception {
        if(keywordService.deleteKeyword(keywordName))
            return new ResponseEntity(CustomBody.of("키워드 삭제에 성공하였습니다.",  HttpStatus.OK), HttpStatus.OK);
        else
            return new ResponseEntity(CustomBody.of("키워드 삭제에 실패하였습니다.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 수정", notes = "사용자가 지정한 키워드를 수정한다.", authorizations = @Authorization(value = "Bearer +accessToken"))
    @PutMapping(value = "/keyword")
    public ResponseEntity modifyKeyword(@RequestParam(name = "keyword-name") String keywordName,
                              @RequestBody Keyword keyword) throws Exception{
        if(keywordService.modifyKeyword(keywordName, keyword))
            return new ResponseEntity(CustomBody.of("키워드 수정에 성공하였습니다.",  HttpStatus.OK), HttpStatus.OK);
        else
            return new ResponseEntity(CustomBody.of("키워드 수정에 실패하였습니다.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Xss
    @ApiOperation(value = "키워드 입력시 검색", notes = "키워드 등록 및 수정 시 자동완성된 키워드를 보여준다.")
    @GetMapping(value = "/keyword/search")
    public ResponseEntity<List<String>> searchKeyword(@RequestParam(name = "keyword") String keyword){
        return new ResponseEntity(CustomBody.of(keywordService.searchKeyword(keyword), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 목록 페이지 - 전체 및 구독하는 사이트별로 조회", notes = "키워드 목록에서 하나의 키워드를 선택한 후 받은 알람을 본다.", authorizations = @Authorization(value = "Bearer +accessToken"))
    @GetMapping(value = "/keyword/list")
    public ResponseEntity<List<Notice>> getKeywordNotice(@RequestParam(name = "keyword-name") String keywordName,
                                                         @RequestParam(name = "site", required = false) String site){
        return new ResponseEntity(CustomBody.of(keywordService.getKeywordNotice(keywordName, site), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 목록 페이지 - 검색", notes = "키워드 목록에서 하나의 키워드를 선택한 후 검색한 후에 검색 결과에 대한 알림 반환", authorizations = @Authorization(value = "Bearer +accessToken"))
    @GetMapping(value = "/keyword/list/search")
    public ResponseEntity<List<Notice>> getSearchNotice(@RequestParam(name = "keyword-name") String keywordName,
                                                        @RequestParam(name = "site", required = false) String site,
                                                        @RequestParam(name = "word") String word){
        return new ResponseEntity(CustomBody.of(keywordService.getSearchNotice(keywordName, site, word), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 목록 페이지 - 알림 삭제", notes = "키워드 목록에서 하나의 키워드를 선택한 후 나온 알림에 대해서 알림 삭제", authorizations = @Authorization(value = "Bearer +accessToken"))
    @PatchMapping(value = "/keyword/list/notice")
    public ResponseEntity deleteNotice(@RequestParam(name = "notice-id") List<Integer> noticeList){
        if(keywordService.deleteNotice(noticeList))
            return new ResponseEntity(CustomBody.of("알림을 삭제했습니다.",  HttpStatus.OK), HttpStatus.OK);
        else
            return new ResponseEntity(CustomBody.of("알림을 삭제하지 못했습니다.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 목록 페이지 - 알림 삭제(실행취소)", notes = "알림 삭제에 대한 실행 취소", authorizations = @Authorization(value = "Bearer +accessToken"))
    @PatchMapping(value = "/keyword/list/notice/undo")
    public ResponseEntity deleteNoticeUndo(@RequestParam("notice-id")List<Integer> noticeList){
        if(keywordService.deleteNoticeUndo(noticeList))
            return new ResponseEntity(CustomBody.of("알림을 삭제를 취소했습니다.",  HttpStatus.OK), HttpStatus.OK);
        else
            return new ResponseEntity(CustomBody.of("알림을 삭제 취소를 실패했습니다.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Xss
    @Auth
    @ApiOperation(value = "키워드 목록 페이지 - 알림 읽음 처리", notes = "키워드 목록에서 하나의 키워드를 선택한 후 나온 알림에 대해서 \n 클릭시 알림 읽음 처리", authorizations = @Authorization(value = "Bearer +accessToken"))
    @PatchMapping(value = "/keyword/list/notice/reading-check")
    public ResponseEntity noticeRead(@RequestParam(name = "notice-id") String noticeId){
        if(keywordService.noticeRead(noticeId))
            return new ResponseEntity(CustomBody.of("알림을 읽었습니다.",  HttpStatus.OK), HttpStatus.OK);
        else
            return new ResponseEntity(CustomBody.of("알림을 읽지 못했습니다.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Xss
    @ApiOperation(value = "키워드 추가하기_추천 키워드", notes = "키워드를 추가 및 수정하는 과정에서 키워드를 추천해준다.")
    @GetMapping(value = "/keyword/recommendation")
    public ResponseEntity recommendKeyword(){
        return new ResponseEntity(CustomBody.of(keywordService.recommendKeyword(), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @ApiOperation(value = "키워드 목록페이지_구독하는 사이트 반환",
            notes = "키워드별로 구독하는 사이트 반환한다.",
            authorizations = @Authorization(value = "Bearer +accessToken"))
    @GetMapping(value = "/keyword/site")
    public ResponseEntity getSiteList(@RequestParam(name="keyword-name") String keywordName){
        return new ResponseEntity(CustomBody.of(keywordService.getSiteList(keywordName), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @ApiOperation(value = "키워드 추가하기_추천 대상", notes = "키워드를 추가 및 수정하는 과정에서 알림받을 대상을 추천해준다.")
    @GetMapping(value = "/keyword/site/recommendation")
    public ResponseEntity recommendSite(){
        return new ResponseEntity(CustomBody.of(keywordService.recommendSite(), HttpStatus.OK), HttpStatus.OK);
    }

    @Xss
    @ApiOperation(value = "키워드 추가하기_대상 입력중", notes = "키워드를 추가 및 수정하는 과정에서 알림받을 대상을 검색했을 시, 대상을 알려준다.")
    @GetMapping(value = "/keyword/site/search")
    public ResponseEntity<List<String>> searchSite(@RequestParam(name = "site") String site){
        return new ResponseEntity(CustomBody.of(keywordService.searchSite(site), HttpStatus.OK), HttpStatus.OK);
    }

}
