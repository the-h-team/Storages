/*
 *  This file is part of Storages.
 *
 *  Copyright 2021 ms5984 (Matt) <https://github.com/ms5984>
 *  Copyright 2021 the-h-team (Sanctum) <https://github.com/the-h-team>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.sanctum.storages.storage;

/**
 * Represents a mutable, indexed ItemStack.
 * <p>
 * <b>Acts as a data-access object; edits are delegated to the provider.</b>
 *
 * @since 1.0.0
 * @author ms5984
 * @see StorageItem
 */
public abstract class StorageSlot extends StorageItem {
    /** The slot index. */
    public final int index;

    /**
     * Create a StorageSlot DAO with the provided index information.
     *
     * @param index the index for the DAO
     */
    protected StorageSlot(int index) {
        this.index = index;
    }

    /**
     * Get the index of this storage slot.
     *
     * @return the index of this storage slot.
     */
    public final int getIndex() {
        return index;
    }
}
