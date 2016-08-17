package com.cognifide.slung.handler.text.filter;

public interface StringEscapeService {

  String escape(String s);

  String unescape(String s);
}
