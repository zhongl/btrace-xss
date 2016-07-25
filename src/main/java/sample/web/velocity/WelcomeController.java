/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.web.velocity;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String view(Map<String, Object> model) throws JsonProcessingException {
        final Bean bean = new Bean("hey", Collections.singleton("1"), Collections.singletonMap("sh", "<"));
        model.put("head", "Change me");
        model.put("bean", bean);
        return "welcome";
    }

    public static class Bean {
        private final String              text;
        private final Collection<String>  coll;
        private final Map<String, String> map;

        public Bean(String text, Collection<String> coll, Map<String, String> map) {
            this.text = text;
            this.coll = coll;
            this.map = map;
        }

        public String getText() {
            return text;
        }

        public Collection<String> getColl() {
            return coll;
        }

        public Map<String, String> getMap() {
            return map;
        }
    }
}
