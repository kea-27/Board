package board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired	//비즈니스 로직을 처리하는 서비스 빈 연결
	private BoardService boardService;
	
	@RequestMapping("/board/openBoardList.do")	//주소 지정
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardList");	//호출된 요청의 결과를 보여줄 뷰 지정
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list",list);
		
		return mv;	//게시글 목록화면 호출
	}
	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board) throws Exception{
		boardService.insertBoard(board);
		return "redirect:/board/openBoardList.do";	//게시글 목록 조회 주소 호출
	}
	
	@RequestMapping("/board/openBoardDetail")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
		
	}
}
