package io.ecp.testmall.board.controller;

import io.ecp.testmall.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
}
