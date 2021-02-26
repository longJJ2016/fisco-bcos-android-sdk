/*
 * Copyright 2014-2020  [fisco-dev]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package org.fisco.bcos.sdk.transaction.manager;

import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.log.Logger;
import org.fisco.bcos.sdk.log.LoggerFactory;
import org.fisco.bcos.sdk.model.EnumNodeVersion;
import org.fisco.bcos.sdk.model.NodeVersion;

public class TransactionProcessorFactory {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessorFactory.class);

    @SuppressWarnings("unlikely-arg-type")
    public static Pair<String, Integer> getChainIdAndGroupId(Client client) {
        NodeVersion version = client.getClientNodeVersion();
        String binaryVersion = version.getNodeVersion().getVersion();
        String supportedVersion = version.getNodeVersion().getSupportedVersion();
        logger.debug(
                "getNodeVersion before createTransactionManager, binaryVerison: {}, supportedVersion:{}",
                binaryVersion,
                supportedVersion);
        // transaction manager for rc1 transaction (without groupId and chainId)
        if (EnumNodeVersion.BCOS_2_0_0_RC1.equals(binaryVersion)
                || EnumNodeVersion.BCOS_2_0_0_RC1.equals(supportedVersion)) {
            logger.debug("createTransactionManager for rc1 node");
            return Pair.of(null, null);
        } else {
            // get chainId
            String chainId = version.getNodeVersion().getChainId();
            // get groupId
            Integer groupId = client.getGroupId();
            logger.debug(
                    "createTransactionManager for >=rc2 node, chainId: {}, groupId: {}",
                    chainId,
                    groupId);
            return Pair.of(chainId, groupId);
        }
    }

    public static TransactionProcessor createTransactionProcessor(
            Client client, CryptoKeyPair cryptoKeyPair) {
        Pair<String, Integer> pair = getChainIdAndGroupId(client);
        return new TransactionProcessor(client, cryptoKeyPair, pair.getRight(), pair.getLeft());
    }

    public static TransactionProcessor createTransactionProcessor(
            Client client,
            CryptoKeyPair cryptoKeyPair,
            String contractName,
            String abi,
            String bin) {
        Pair<String, Integer> pair = getChainIdAndGroupId(client);
        return new TransactionProcessor(
                client, cryptoKeyPair, pair.getRight(), pair.getLeft(), contractName, abi, bin);
    }
}
