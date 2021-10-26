package board.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data	//롬복 어노테이션, 모든 필드의 getter, setter를 생성하고 toString, hashCode, equals 메서드 생성
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private LocalDateTime createdDatetime;
	private String updaterId;
	private LocalDateTime updatedDatetime;
	private List<BoardFileDto> fileList;
}
