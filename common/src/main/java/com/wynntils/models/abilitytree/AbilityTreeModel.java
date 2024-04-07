/*
 * Copyright © Wynntils 2023-2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.models.abilitytree;

import com.google.common.reflect.TypeToken;
import com.wynntils.core.components.Managers;
import com.wynntils.core.components.Model;
import com.wynntils.core.net.Download;
import com.wynntils.core.net.UrlId;
import com.wynntils.models.abilitytree.parser.AbilityTreeParser;
import com.wynntils.models.abilitytree.type.AbilityTreeInfo;
import com.wynntils.models.abilitytree.type.ParsedAbilityTree;
import com.wynntils.models.character.type.ClassType;
import com.wynntils.screens.abilities.CustomAbilityTreeScreen;
import com.wynntils.utils.mc.McUtils;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AbilityTreeModel extends Model {
    public static final int ABILITY_TREE_PAGES = 7;
    public static final AbilityTreeParser ABILITY_TREE_PARSER = new AbilityTreeParser();
    public static final AbilityTreeContainerQueries ABILITY_TREE_CONTAINER_QUERIES = new AbilityTreeContainerQueries();

    private Map<ClassType, AbilityTreeInfo> abilityTreeMap = new EnumMap<>(ClassType.class);
    private ParsedAbilityTree currentAbilityTree;

    public AbilityTreeModel() {
        super(List.of());

        reloadData();
    }

    @Override
    public void reloadData() {
        Download dl = Managers.Net.download(UrlId.DATA_STATIC_ABILITIES);
        dl.handleReader(reader -> {
            Type type = new TypeToken<HashMap<String, AbilityTreeInfo>>() {}.getType();

            Map<String, AbilityTreeInfo> abilityMap = Managers.Json.GSON.fromJson(reader, type);

            Map<ClassType, AbilityTreeInfo> tempMap = new EnumMap<>(ClassType.class);

            abilityMap.forEach((key, value) -> tempMap.put(ClassType.fromName(key), value));

            abilityTreeMap = tempMap;
        });
    }

    public boolean isLoaded() {
        return !abilityTreeMap.isEmpty();
    }

    public void setCurrentAbilityTree(ParsedAbilityTree currentAbilityTree) {
        this.currentAbilityTree = currentAbilityTree;

        if (McUtils.mc().screen instanceof CustomAbilityTreeScreen customAbilityTreeScreen) {
            customAbilityTreeScreen.updateAbilityTree();
        }
    }

    public Optional<ParsedAbilityTree> getCurrentAbilityTree() {
        return Optional.ofNullable(currentAbilityTree);
    }

    public AbilityTreeInfo getAbilityTree(ClassType type) {
        return abilityTreeMap.get(type);
    }
}
