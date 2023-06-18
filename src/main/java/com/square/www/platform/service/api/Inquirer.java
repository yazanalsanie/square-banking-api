package com.square.www.platform.service.api;

import com.square.www.platform.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface Inquirer {
    /**
     * @apiNote gets a bank account setails
     * @param accountId
     * @return {@link Account}
     * **/
    Account inquiry(UUID accountId);
}
