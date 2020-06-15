package com.pitst.blog.controllers;

import com.pitst.blog.models.Post;
import com.pitst.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String addPost(@RequestParam String title, @RequestParam String anons,
                          @RequestParam String fullText, Model model) {
        postRepository.save(new Post(title, anons, fullText));
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/blog";
        }
        model.addAttribute("post", post.get());
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/blog";
        }
        model.addAttribute("post", post.get());
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") Long id, @RequestParam String title,
                               @RequestParam String anons, @RequestParam String fullText) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/blog";
        }
        Post editPost = post.get();
        editPost.setTitle(title);
        editPost.setAnons(anons);
        editPost.setFullText(fullText);
        postRepository.save(editPost);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable(value = "id") Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/blog";
        }
        postRepository.delete(post.get());
        return "redirect:/blog";
    }
}
