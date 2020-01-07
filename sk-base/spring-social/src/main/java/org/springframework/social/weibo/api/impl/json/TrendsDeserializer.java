/*
 * Copyright 2011 France Telecom R&D Beijing Co., Ltd 北京法国电信研发中心有限公司
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.weibo.api.impl.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.social.weibo.api.Trends;
import org.springframework.social.weibo.api.Trends.Trend;

public class TrendsDeserializer extends JsonDeserializer<SortedSet<Trends>> {

    private static final Log logger = LogFactory
            .getLog(TrendsDeserializer.class.getName());

    private static final Comparator<? super Trends> comparator = new Comparator<Trends>() {

        @Override
        public int compare(Trends o1, Trends o2) {
            if (o1.getDate() == null) {
                if (o2.getDate() == null) {
                    return 0;
                } else {
                    return 1;
                }
            } else if (o2.getDate() == null) {
                return -1;
            } else {
                return o1.getDate().compareTo(o2.getDate());
            }
        }
    };

    @Override
    public SortedSet<Trends> deserialize(JsonParser jp,
                                         DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        TreeSet<Trends> result = new TreeSet<Trends>(comparator);
        TreeNode treeNode = jp.readValueAsTree();

        Iterator<String> fieldNames = treeNode.fieldNames();

        while (fieldNames.hasNext()) {
            Trends trends = new Trends();
            try {
                String filedName = fieldNames.next();
                dateFormat.applyPattern(retrieveDateFormatPattern(filedName));
                trends.setDate(dateFormat.parse(filedName));
                TreeNode trendsNode = treeNode.get(filedName);
                if (trendsNode.isArray()) {
                    for (int i = 0; i < trendsNode.size(); i++) {
                        JsonParser nodeParser = trendsNode.get(i).traverse();
                        nodeParser.setCodec(jp.getCodec());
                        Trend readValueAs = nodeParser.readValueAs(Trend.class);
                        trends.getTrends().add(readValueAs);
                    }
                }
                result.add(trends);
            } catch (ParseException e) {
                logger.warn("Unable to parse date", e);
            }
        }

        return result;
    }

    private String retrieveDateFormatPattern(String key) {
        String result = null;
        switch (key.length()) {
            case 19:
                result = "yyyy-MM-dd HH:mm:ss";
                break;
            case 16:
                result = "yyyy-MM-dd HH:mm";
                break;
            default:
                result = "yyyy-MM-dd";
                break;
        }
        return result;
    }
}
