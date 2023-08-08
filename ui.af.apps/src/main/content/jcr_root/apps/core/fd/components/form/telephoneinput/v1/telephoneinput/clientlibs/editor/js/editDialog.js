/*******************************************************************************
 * Copyright 2023 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
(function ($) {
    "use strict";

    var EDIT_DIALOG = ".cmp-adaptiveform-telephoneinput__editdialog",
        TELEPHONEINPUT_DISPLAYPATTERN = EDIT_DIALOG + " .cmp-adaptiveform-telephoneinput__displaypattern",
        TELEPHONEINPUT_DISPLAYFORMAT = EDIT_DIALOG + " .cmp-adaptiveform-telephoneinput__displayformat",
        TELEPHONEINPUT_VALIDATIONPATTERN = EDIT_DIALOG + " .cmp-adaptiveform-telephoneinput__validationpattern",
        TELEPHONEINPUT_VALIDATIONFORMAT = EDIT_DIALOG + " .cmp-adaptiveform-telephoneinput__validationformat",
        Utils = window.CQ.FormsCoreComponents.Utils.v1;

    function handleDisplayPatternDropDown(dialog) {
        Utils.handlePatternDropDown(dialog, TELEPHONEINPUT_DISPLAYPATTERN, TELEPHONEINPUT_DISPLAYFORMAT);
    }

    function handleDisplayFormat(dialog) {
        Utils.handlePatternFormat(dialog, TELEPHONEINPUT_DISPLAYPATTERN, TELEPHONEINPUT_DISPLAYFORMAT);
    }

    function handleValidationPatternDropDown(dialog) {
        Utils.handlePatternDropDown(dialog, TELEPHONEINPUT_VALIDATIONPATTERN, TELEPHONEINPUT_VALIDATIONFORMAT);
    }

    function handleValidationFormat(dialog) {
        Utils.handlePatternFormat(dialog, TELEPHONEINPUT_VALIDATIONPATTERN, TELEPHONEINPUT_VALIDATIONFORMAT);
    }

    Utils.initializeEditDialog(EDIT_DIALOG)(handleValidationPatternDropDown, handleValidationFormat,
        handleDisplayPatternDropDown, handleDisplayFormat);
})(jQuery);
