/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2022 Adobe
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

import java.io.IOException;
import java.util.Map;

import javax.annotation.Nullable;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.jetbrains.annotations.NotNull;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.forms.core.components.internal.form.FormConstants;
import com.adobe.cq.forms.core.components.models.form.StaticImage;
import com.adobe.cq.forms.core.components.util.AbstractFieldImpl;
import com.day.cq.wcm.foundation.Image;

@Model(
    adaptables = { SlingHttpServletRequest.class, Resource.class },
    adapters = { StaticImage.class, ComponentExporter.class },
    resourceType = { FormConstants.RT_FD_FORM_IMAGE_V1 })
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class StaticImageImpl extends AbstractFieldImpl implements StaticImage {

    private Image image;

    @SlingObject
    private Resource resource;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected String altText;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected String imageSrc;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected Boolean excludeFromDor;

    /**
     * Returns the source where the image is present.
     *
     * @return String representing source of the image.
     */
    @Override
    public String getImageSrc() throws RepositoryException, IOException {
        image = new Image(this.resource);

        Boolean containsData = (image.getData() != null);
        if (containsData) {
            image.setSelector(".img");
            return image.getSrc();
        } else {
            return "";
        }
    }

    /**
     * Returns the alternate text of the Image configured in the authoring dialog.
     * 
     * @return String representing alternate text
     */
    @Override
    public String getAltText() {
        return altText;
    }

    /**
     * Returns the excluded from dor field of the Image configured in the authoring dialog.
     *
     * @return String representing alternate text
     */
    @Override
    public Boolean isExcludeFromDor() {
        return excludeFromDor;
    }

    @Override
    public @NotNull Map<String, Object> getCustomProperties() {
        Map<String, Object> customProperties = super.getCustomProperties();
        if (altText != null) {
            customProperties.put("altText", altText);
        }
        if (excludeFromDor != null) {
            customProperties.put("excludeFromDor", excludeFromDor);
        }
        if (imageSrc != null) {
            customProperties.put("imageSrc", imageSrc);
        }
        return customProperties;
    }

}
