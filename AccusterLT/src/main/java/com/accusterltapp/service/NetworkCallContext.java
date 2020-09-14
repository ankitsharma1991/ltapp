package com.accusterltapp.service;
/***
 * 
 *
 */
public interface NetworkCallContext {
    void handleResponse(Object response, String type);
    void handleServerError(Object response, String type);
}
