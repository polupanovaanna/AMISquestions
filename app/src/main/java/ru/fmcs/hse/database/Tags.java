package ru.fmcs.hse.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tags {
    static String GROUP_ID = "Tag";
    private ArrayList<String> tags = new ArrayList<>(Arrays.asList("C++", "python", "java"));

    public void addTag(String newtag) {
        tags.add(newtag);
    }

    public TagsTable getTagsTable() {
        return new TagsTable();
    }

    //Tags создается один на всю программу, затем для выпадающих списков используем TagsTable
    //Для поиска можно использовать compare для сравнения отмеченных тэгов и тегов в посте

    public class TagsTable {
        private Map<String, Boolean> tagsMap = new HashMap<>();

        public TagsTable() {
            for (String tag : tags) {
                tagsMap.put(tag, false);
            }
        }

        public String[] getAllTags() {
            Object[] tmp = tagsMap.keySet().toArray();
            String[] ttmp = new String[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                ttmp[i] = (String) tmp[i];
            }
            return ttmp;
        }

        public void markTag(String tag) {
            tagsMap.put(tag, true);
        }

        public void unmarkTag(String tag) {
            tagsMap.put(tag, false);
        }

        public Set<String> getMarkedTags() {
            Set<String> ans = new HashSet<>();
            for (Map.Entry<String, Boolean> pair : tagsMap.entrySet()) {
                if (pair.getValue()) {
                    ans.add(pair.getKey());
                }
            }
            return ans;
        }

        //метод хочет на вход список, чтобы список вызвали один раз предыдущим методом, а потом применяли ко всем постам...
        public boolean compareToPostTags(HashSet<String> markedTags, HashSet<String> postTags) {
            for (String tag : markedTags) {
                if (!postTags.contains(tag)) {
                    return false;
                }
            }
            return true;
        }
    }

}
