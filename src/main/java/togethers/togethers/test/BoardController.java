package togethers.togethers.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm()
    {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardwritePro(Board board, MultipartFile file) throws Exception //input name:title안에 변수명이되어 controller에 넘어온다
    {
        boardService.write(board,file);
        return "boardwrite";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("List",boardService.boardList()) ;
        return "boardlist";
    }


    @GetMapping("/board/view") //8081/board/view?id=1 하면 1의 값이 id매개변수에 담긴다
    public String boardview(Model model, Integer id)
    {
        model.addAttribute("board",boardService.boardview(id));
        System.out.println(boardService.boardview(id).getFilepath());
        System.out.println(boardService.boardview(id).getFilename());
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id)
    {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}") //id 부분이 @Pathvariable에 인식이 되서 Integer형태 바뀌고 id 변수명으로 지정한다
    public String boardModify(@PathVariable("id")Integer id, Model model)
    {
        model.addAttribute("board",boardService.boardview(id));
        return "boardmodify";
    }

    @PostMapping("/board/modify/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board,MultipartFile file)throws Exception
    {
        Board OriBoard = boardService.boardview(id); // uri에 id가 담겨져온다는건 기존에 게시물, 기존의 게시물 id를 통해 게시물 찾음
        OriBoard.setTitle(board.getTitle());
        OriBoard.setContent(board.getContent());

        boardService.write(OriBoard,file);


        return "redirect:/board/list";
    }


}
