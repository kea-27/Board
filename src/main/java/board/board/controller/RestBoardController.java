package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;

@Controller
public class RestBoardController {
	
	@Autowired
	private BoardService boardService;
	
	//기존 컨트롤러에서는 주소만 입력했지만, 
	//RESTful 서비스에서는 주소와 요청방법을 반드시 지정해야함
	//value="주소", method=요청방식
	@RequestMapping(value="/board", method=RequestMethod.GET)	
	public ModelAndView openBoardList() throws Exception{
		
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	/* 주소는 똑같고 요청방식이 GET과 POST로 다름 */
	/* /board/write라는 주소를 호출할 때, 
	 * GET방식으로 요청하면 게시글 작성화면이 호출되고
	 * POST방식으로 요청하면 게시글이 등록됨 */
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		
		return "/board/restBoardWrite";
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		boardService.insertBoard(board, multipartHttpServletRequest);
		
		return "redirect:/board";
	}
	/* ******************************************************************* */
	//동일한 URI라도 요청방식에 따라서 다른 기능을 수행함
	/* URI : /board/{boardIdx}
	 * 요청방식 : 
	 * 		GET - 게시물 상세조회
	 * 		PUT - 게시글 수정 
	 * 		DELETE - 게시글 삭제 
	 */
	
	//@PathVariable 어노테이션은 메서드의 파라미터가 URI의 변수로 사용되는 것을 의미함
	/* boardIdx라는 이름의 파라미터가 URI의 {boardIdx}에 바인드됨
	 * 파라미터(게시글번호)가 바뀔 때마다 호출되는 URI도 같이 변경됨 */
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardDto board) throws Exception{
		
		boardService.updateBoard(board);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
	/* ******************************************************************* */
	
	@RequestMapping(value="/board/file", method=RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposetion",  "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encodeing", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		}
	}
}
