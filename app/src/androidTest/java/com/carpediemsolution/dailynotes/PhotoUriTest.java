package com.carpediemsolution.dailynotes;

import android.content.Context;

import com.carpediemsolution.dailynotes.utils.UriValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PhotoUriTest {

    UriValidator uriValidator;
    Context mockContext;
    private String photoUri;

    @Before
    void setUp(){
        mockContext = Mockito.mock(Context.class);
        uriValidator = new UriValidator(mockContext);
    }

    @Test
    public void validate() throws Exception {
        assertTrue(uriValidator.isFile(photoUri));
    }
}
