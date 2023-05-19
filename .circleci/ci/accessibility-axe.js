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

const fs = require('fs');
const AxeBuilder = require('@axe-core/webdriverjs');
const WebDriver = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');
const { createHtmlReport } = require('axe-html-reporter');


const calculateAccessibility = async () => {

    const options = new chrome.Options();
    const driver = new WebDriver.Builder().forBrowser('chrome').setChromeOptions(options).build();

    try {
        await driver.get('http://localhost:4502/content/dam/formsanddocuments/core-components-it/samples/wizard/repeatability/jcr:content?wcmmode=disabled');

        await new Promise((resolve) => setTimeout(resolve, 4000));
//        await driver.findElement(WebDriver.By.xpath("//button[@aria-expanded='false']/coral-accordion-item-label[contains(text(),'Sign in locally')]")).click();
        const usernameInput = await driver.findElement(WebDriver.By.id('username'));
        await usernameInput.sendKeys('admin');
        const passwordInput = await driver.findElement(WebDriver.By.id('password'));
        await passwordInput.sendKeys('admin');
        await driver.findElement(WebDriver.By.id('submit-button')).click();
        await new Promise((resolve) => setTimeout(resolve, 4000));
        await driver.wait(async () => {
          const readyState = await driver.executeScript('return document.readyState');
          return readyState === 'complete';
        });
        await driver.getPageSource();

        const axeBuilder = new AxeBuilder(driver);
        const results = await axeBuilder.analyze();
        const reportHTML = createHtmlReport({
                                              results: results,
                                              options: {
                                                  projectKey: 'aem-core-forms-components'
                                              },
                                          });
        fs.writeFileSync('accessibility-report.html', reportHTML);

        if (results.violations.length > 0) {
           // impact can be 'critical', 'serious', 'moderate', 'minor', 'unknown'
           results.violations.filter(violation => ['critical', 'serious', 'moderate'].includes(violation.impact)).forEach(async violation => {
                console.log("Error: Accessibility violations found, please refer the report under artifacts to fix the same!")
                 process.exit(1); // fail pipeline
           })
           console.log("results.violations--->>>", results.violations);
        }
    }
    catch (e) {
        console.log("Some error occured in calculating accessibility", e)
    }
}

calculateAccessibility()