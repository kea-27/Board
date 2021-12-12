package board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.board.dto.BoardDto;
import board.board.service.BoardService;

// @RestController = @Controller + @ResponseBody
/* @RestController 어노테이션을 사용하면 해당 API의 응답 결과를 웹 응답 바디(Web response body)를 이용해서 보내줌
 * 일반적으로는 서버와 클라이언트의 통신에 JSON 형식을 사용함
 * @RestController 어노테이션을 이용하면 결괏값을 JSON 형식으로 만들어줌*/
@RestController	
public class RestBoardApiController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/api/board", method=RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception{
		
		//기존에는 ModelAndView 클래스에 목록 조회결과를 담아서 뷰에 보냈던 것과 달리
		//조회 결과를 바로 API의 응답결과로 사용함
		//게시글 목록 조회는 List<BoardDto> 형식이고 이를 바로 JSON 형태로 반환
		return boardService.selectBoardList();
	}
	
	@RequestMapping(value="/api/board/write", method=RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception{
		
		boardService.insertBoard(board, null);
	}
	
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		
		return boardService.selectBoardDetail(boardIdx);
	}
	
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception{
		
		boardService.updateBoard(board);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
}
