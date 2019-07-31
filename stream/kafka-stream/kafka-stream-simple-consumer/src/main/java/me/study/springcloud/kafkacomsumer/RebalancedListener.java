/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-07-31T22:46:48.721+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.kafkacomsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.cloud.stream.binder.kafka.KafkaBindingRebalanceListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class RebalancedListener implements KafkaBindingRebalanceListener {

    @Override
    public void onPartitionsRevokedBeforeCommit(String bindingName,
                                                Consumer<?, ?> consumer,
                                                Collection<TopicPartition> partitions) {
        log.debug("before comment");
    }

    @Override
    public void onPartitionsRevokedAfterCommit(String bindingName,
                                               Consumer<?, ?> consumer,
                                               Collection<TopicPartition> partitions) {
        log.debug("after comment");
    }

    @Override
    public void onPartitionsAssigned(String bindingName,
                                     Consumer<?, ?> consumer,
                                     Collection<TopicPartition> partitions,
                                     boolean initial) {


        partitions.forEach(
                p -> consumer.seek(p, 1)
        );


    }
}
