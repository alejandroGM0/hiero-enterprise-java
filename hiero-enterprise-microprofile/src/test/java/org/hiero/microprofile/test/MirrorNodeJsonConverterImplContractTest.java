package org.hiero.microprofile.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.ContractId;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.StringReader;
import java.time.Instant;
import java.util.List;
import org.hiero.base.data.ContractLog;
import org.hiero.base.data.ContractResult;
import org.hiero.base.data.NftTransactionTransfer;
import org.hiero.base.protocol.data.TransactionType;
import org.hiero.microprofile.implementation.MirrorNodeJsonConverterImpl;
import org.junit.jupiter.api.Test;

class MirrorNodeJsonConverterImplContractTest {

  private final MirrorNodeJsonConverterImpl converter = new MirrorNodeJsonConverterImpl();

  @Test
  void toContractResultsReturnsEmptyWhenKeyMissing() {
    final JsonObject jsonObject = parse("{\"links\":{\"next\":null}}");

    final List<ContractResult> result = converter.toContractResults(jsonObject);

    assertTrue(result.isEmpty());
  }

  @Test
  void toContractResultsReturnsEmptyForEmptyArray() {
    final JsonObject jsonObject = parse("{\"results\":[],\"links\":{\"next\":null}}");

    final List<ContractResult> result = converter.toContractResults(jsonObject);

    assertTrue(result.isEmpty());
  }

  @Test
  void toContractResultsParsesSingleEntry() {
    final JsonObject jsonObject = parse(contractResultsJson());

    final List<ContractResult> result = converter.toContractResults(jsonObject);

    assertEquals(1, result.size());
    final ContractResult contractResult = result.get(0);
    assertEquals(ContractId.fromString("0.0.5005"), contractResult.contractId());
    assertEquals("0x000000000000000000000000000000000000138d", contractResult.address());
    assertEquals(10L, contractResult.amount());
    assertEquals(100_000L, contractResult.gasLimit());
    assertEquals("SUCCESS", contractResult.result());
    assertEquals("0x1", contractResult.status());
    assertEquals(Instant.ofEpochSecond(1_586_567_700L, 453_054_000), contractResult.timestamp());
    assertEquals(List.of(ContractId.fromString("0.0.6006")), contractResult.createdContractIds());
  }

  @Test
  void toContractLogsReturnsEmptyWhenKeyMissing() {
    final JsonObject jsonObject = parse("{\"links\":{\"next\":null}}");

    final List<ContractLog> result = converter.toContractLogs(jsonObject);

    assertTrue(result.isEmpty());
  }

  @Test
  void toContractLogsReturnsEmptyForEmptyArray() {
    final JsonObject jsonObject = parse("{\"logs\":[],\"links\":{\"next\":null}}");

    final List<ContractLog> result = converter.toContractLogs(jsonObject);

    assertTrue(result.isEmpty());
  }

  @Test
  void toContractLogsParsesSingleEntry() {
    final JsonObject jsonObject = parse(contractLogsJson());

    final List<ContractLog> result = converter.toContractLogs(jsonObject);

    assertEquals(1, result.size());
    final ContractLog contractLog = result.get(0);
    assertEquals(ContractId.fromString("0.0.5005"), contractLog.contractId());
    assertEquals("0x000000000000000000000000000000000000138d", contractLog.address());
    assertEquals(2, contractLog.index());
    assertEquals(List.of("0xaaaaaaaa", "0xbbbbbbbb"), contractLog.topics());
    assertEquals(10L, contractLog.blockNumber());
    assertEquals(ContractId.fromString("0.0.5005"), contractLog.rootContractId());
    assertEquals(Instant.ofEpochSecond(1_586_567_700L, 453_054_000), contractLog.timestamp());
    assertEquals(1, contractLog.transactionIndex());
  }

  @Test
  void toNftTransactionTransfersReturnsEmptyWhenKeyMissing() {
    final JsonObject jsonObject = parse("{\"links\":{\"next\":null}}");

    final List<NftTransactionTransfer> result = converter.toNftTransactionTransfers(jsonObject);

    assertTrue(result.isEmpty());
  }

  @Test
  void toNftTransactionTransfersReturnsEmptyForEmptyArray() {
    final JsonObject jsonObject = parse("{\"transactions\":[],\"links\":{\"next\":null}}");

    final List<NftTransactionTransfer> result = converter.toNftTransactionTransfers(jsonObject);

    assertTrue(result.isEmpty());
  }

  @Test
  void toNftTransactionTransfersParsesSingleEntry() {
    final JsonObject jsonObject = parse(nftTransactionHistoryJson());

    final List<NftTransactionTransfer> result = converter.toNftTransactionTransfers(jsonObject);

    assertEquals(1, result.size());
    final NftTransactionTransfer transfer = result.get(0);
    assertEquals(Instant.ofEpochSecond(1_618_591_023L, 997_420_021), transfer.consensusTimestamp());
    assertEquals(false, transfer.isApproval());
    assertEquals(0, transfer.nonce());
    assertEquals(AccountId.fromString("0.0.11"), transfer.receiverAccountId());
    assertEquals(AccountId.fromString("0.0.10"), transfer.senderAccountId());
    assertEquals("0.0.19789-1618591023-997420021", transfer.transactionId());
    assertEquals(TransactionType.CRYPTO_TRANSFER, transfer.type());
  }

  private static JsonObject parse(final String json) {
    return Json.createReader(new StringReader(json)).readObject();
  }

  private static String contractResultsJson() {
    return """
        {
          "results": [
            {
              "access_list": null,
              "address": "0x000000000000000000000000000000000000138d",
              "amount": 10,
              "block_gas_used": 2000,
              "block_hash": "0x6ceecd8bb224da491",
              "block_number": 10,
              "bloom": "0x549358c4c2e573e02410ef7b5a5ffa5f36dd7398",
              "call_result": "0x2b",
              "chain_id": "0x0127",
              "contract_id": "0.0.5005",
              "created_contract_ids": ["0.0.6006"],
              "error_message": null,
              "failed_initcode": "0x856739",
              "from": "0x00000000000000000000000000000000000003e9",
              "function_parameters": "0xbb",
              "gas_consumed": 35000,
              "gas_limit": 100000,
              "gas_price": "0x4a817c800",
              "gas_used": 80000,
              "hash": "0xfebbaa29c513d124a6377246ea3506ad917d740c21a88f61a1c55ba338fc2bb1",
              "max_fee_per_gas": "0x5",
              "max_priority_fee_per_gas": "0x100",
              "nonce": 1,
              "r": "0xd693b532",
              "result": "SUCCESS",
              "s": "0x24e9c602",
              "status": "0x1",
              "timestamp": "1586567700.453054000",
              "to": "0x000000000000000000000000000000000000138d",
              "transaction_index": 1,
              "type": 2,
              "v": 1
            }
          ],
          "links": {"next": null}
        }
        """;
  }

  private static String contractLogsJson() {
    return """
        {
          "logs": [
            {
              "address": "0x000000000000000000000000000000000000138d",
              "bloom": "0x549358c4c2e573e02410ef7b5a5ffa5f36dd7398",
              "contract_id": "0.0.5005",
              "data": "0x00000000000000000000000000000000000000000000000000000000000000fa",
              "index": 2,
              "topics": ["0xaaaaaaaa", "0xbbbbbbbb"],
              "block_hash": "0x553f9311833391c0",
              "block_number": 10,
              "root_contract_id": "0.0.5005",
              "timestamp": "1586567700.453054000",
              "transaction_hash": "0x397022d1e5baeb89d0ab66e6bf602640610e6fb7e55d78638db861e2c6339aa9",
              "transaction_index": 1
            }
          ],
          "links": {"next": null}
        }
        """;
  }

  private static String nftTransactionHistoryJson() {
    return """
        {
          "transactions": [
            {
              "consensus_timestamp": "1618591023.997420021",
              "is_approval": false,
              "nonce": 0,
              "receiver_account_id": "0.0.11",
              "sender_account_id": "0.0.10",
              "transaction_id": "0.0.19789-1618591023-997420021",
              "type": "CRYPTOTRANSFER"
            }
          ],
          "links": {"next": null}
        }
        """;
  }
}
