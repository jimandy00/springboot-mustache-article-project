package com.mustache.article.controller;

import com.mustache.article.dto.ArticleDto;
import com.mustache.article.entity.Article;
import com.mustache.article.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new")
    public String createPage() {
        return "new";
    }

    @PostMapping("")
    public String add(ArticleDto articleDto) {
        log.info(articleDto.getTitle());
        return ""; // 임시
    }

    @GetMapping("/{id}")
    public String selectString(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isEmpty()) {
            model.addAttribute("article", optArticle.get());
            return "show";
        } else {
            return "error";
        }
    }

    @GetMapping("/list")
    public String list() {
        return "list";
    }

    @GetMapping("")
    public String index() {
        return "redirect:/articles/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isEmpty()) {
            model.addAttribute("article", optArticle.get());
            return "articles/edit";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "articles/error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDto articleDto, Model model) {
        log.info("title:{} content:{}", articleDto.getTitle(), articleDto.getContent());
        Article article = articleRepository.save(articleDto.toEntity());
        model.addAttribute("article", article);
        return String.format("redirect:/articles/%d", article.getId());
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleRepository.findById(id);
        return "redirect:/articles";
    }

}
