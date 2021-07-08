/*
 *  This file is part of storages.
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
package com.github.sanctum.storages.exceptions;

public class InventoryHolderException extends ProviderException {
    private static final long serialVersionUID = 5007244222311834724L;

    public InventoryHolderException() {
    }
    public InventoryHolderException(String message) {
        super(message);
    }
    public InventoryHolderException(String message, Throwable cause) {
        super(message, cause);
    }
    public InventoryHolderException(Throwable cause) {
        super(cause);
    }
}
