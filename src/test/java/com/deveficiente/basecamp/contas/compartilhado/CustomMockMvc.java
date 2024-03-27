package com.deveficiente.basecamp.contas.compartilhado;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@Component
public class CustomMockMvc {

  @Autowired private MockMvc mockMvc;

  public ResultActions get(String url, String email) {
    try {

      var content =
          MockMvcRequestBuilders.get(url)              
              .accept(APPLICATION_JSON_VALUE);

      return mockMvc.perform(content);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ResultActions get(String url) {
    return this.get(url, "aluno@zup.com.br");
  }

  public ResultActions post(String url, Map<String, Object> parameters) {
    try {

      MockHttpServletRequestBuilder content =
          MockMvcRequestBuilders.post(url)
          	  .contentType(APPLICATION_JSON_VALUE)
              .accept(APPLICATION_JSON_VALUE);

      if (!parameters.isEmpty()) {
        content.content(new ObjectMapper().writeValueAsString(parameters));
      }

      return mockMvc.perform(content);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ResultActions post(String url) {
    return this.post(url, new HashMap<>());
  }
}
