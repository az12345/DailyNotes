package com.carpediemsolution.dailynotes.mockito;

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

    private UriValidator uriValidator;
    private Context mockContext;
    private String photoUri;

    @Before
    public void setUp(){
        mockContext = Mockito.mock(Context.class);
        uriValidator = new UriValidator(mockContext);
    }

    @Test
    public void validate() throws Exception {
        assertTrue(uriValidator.isFile(photoUri));
    }
}
