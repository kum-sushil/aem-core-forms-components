/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2024 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package com.adobe.cq.forms.core.components.internal.models.v1.form;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.adobe.cq.forms.core.Utils;
import com.adobe.cq.forms.core.components.datalayer.FormComponentData;
import com.adobe.cq.forms.core.components.internal.form.FormConstants;
import com.adobe.cq.forms.core.components.models.form.FieldType;
import com.adobe.cq.forms.core.components.models.form.Password;
import com.adobe.cq.forms.core.context.FormsCoreComponentTestContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(AemContextExtension.class)
public class PasswordImplTest {

    private static final String BASE = "/form/password";
    private static final String CONTENT_ROOT = "/content";
    private static final String PATH_PASSWORD_DATALAYER = CONTENT_ROOT + "/password-datalayer";
    private static final String PATH_PASSWORD_CUSTOMIZED = CONTENT_ROOT + "/password-customized";

    private static final String PATH_PASSWORD = CONTENT_ROOT + "/password";

    private static final String PATH_PASSWORD_PATTERN = CONTENT_ROOT + "/password-pattern";

    private final AemContext context = FormsCoreComponentTestContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.load().json(BASE + FormsCoreComponentTestContext.TEST_CONTENT_JSON, CONTENT_ROOT);
    }

    @Test
    void testExportedType() throws Exception {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(FormConstants.RT_FD_FORM_PASSWORD_V1, password.getExportedType());
        Password passwordMock = Mockito.mock(Password.class);
        Mockito.when(passwordMock.getExportedType()).thenCallRealMethod();
        assertEquals("", passwordMock.getExportedType());
    }

    @Test
    void testJSONExport() throws Exception {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_PATTERN, Password.class, context);
        Utils.testJSONExport(password, Utils.getTestExporterJSONPath(BASE, PATH_PASSWORD));
    }

    @Test
    void testFieldType() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(FieldType.PASSWORD.getValue(), password.getFieldType());
    }

    @Test
    void testGetLabel() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals("pwd", password.getLabel().getValue());
    }

    @Test
    void testPlaceholder() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals("Enter valid password", password.getPlaceHolder());
    }

    @Test
    void testGetName() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals("password", password.getName());

    }

    @Test
    void testDorProperties() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(true, password.getDorProperties().get("dorExclusion"));
        assertEquals("4", password.getDorProperties().get("dorColspan"));
        assertEquals("Text1", password.getDorProperties().get("dorBindRef"));

    }

    @Test
    void testGetDescription() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals("password field", password.getDescription());
    }

    @Test
    void testGetRequired() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(true, password.isRequired());
    }

    @Test
    void testIsEnabled() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(true, password.isEnabled());
    }

    @Test
    void testIsEnabledForCustomized() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(true, password.isEnabled());
    }

    @Test
    void testIsReadOnly() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(false, password.isReadOnly());
    }

    @Test
    void testIsReadOnlyForCustomized() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(false, password.isReadOnly());
    }

    @Test
    void testMinLength() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(5, password.getMinLength().intValue());
    }

    @Test
    void testMaxLength() {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals(10, password.getMaxLength().intValue());
    }

    @Test
    void testGetDisplayFormat() throws Exception {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_CUSTOMIZED, Password.class, context);
        assertEquals("password", password.getFormat());
    }

    @Test
    void testGetPattern() throws Exception {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_PATTERN, Password.class, context);
        assertEquals("^(?=.*\\d.*\\d)[A-Za-z\\d!@]+$", password.getPattern());
    }

    @Test
    void testDataLayerProperties() throws IllegalAccessException {
        Password password = Utils.getComponentUnderTest(PATH_PASSWORD_DATALAYER, Password.class, context);
        FieldUtils.writeField(password, "dataLayerEnabled", true, true);
        FormComponentData dataObject = (FormComponentData) password.getData();
        assert (dataObject != null);
        assert (dataObject.getId()).equals("password-1c7bc238a6");
        assert (dataObject.getType()).equals("core/fd/components/form/password/v1/password");
        assert (dataObject.getTitle()).equals("Full Name");
        assert (dataObject.getFieldType()).equals("password");
        assert (dataObject.getDescription()).equals("Enter Full Name");
    }
}