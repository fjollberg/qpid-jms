/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.qpid.jms;

/**
 * Policy object that allows for configuration of options that affect when
 * a JMS MessageProducer will result in AMQP presettled message sends.
 */
public class JmsPresettlePolicy {

    private boolean presettleAll;

    private boolean presettleProducers;
    private boolean presettleTopicProducers;
    private boolean presettleQueueProducers;
    private boolean presettleTransactedProducers;

    private boolean presettleConsumers;
    private boolean presettleTopicConsumers;
    private boolean presettleQueueConsumers;

    public JmsPresettlePolicy() {
    }

    public JmsPresettlePolicy(JmsPresettlePolicy source) {
        this.presettleAll = source.presettleAll;
        this.presettleProducers = source.presettleProducers;
        this.presettleTopicProducers = source.presettleTopicProducers;
        this.presettleQueueProducers = source.presettleQueueProducers;
        this.presettleTransactedProducers = source.presettleTransactedProducers;
        this.presettleConsumers = source.presettleConsumers;
        this.presettleTopicConsumers = source.presettleTopicConsumers;
        this.presettleQueueConsumers = source.presettleQueueConsumers;
    }

    public JmsPresettlePolicy copy() {
        return new JmsPresettlePolicy(this);
    }

    /**
     * @return the presettleAll setting for this policy
     */
    public boolean isPresettleAll() {
        return presettleAll;
    }

    /**
     * Sets the presettle all sends option.  When true all MessageProducers
     * will send their messages presettled.
     *
     * @param presettleAll
     *      the presettleAll value to apply.
     */
    public void setPresettleAll(boolean presettleAll) {
        this.presettleAll = presettleAll;
    }

    /**
     * @return the presettleProducers setting for this policy.
     */
    public boolean isPresettleProducers() {
        return presettleProducers;
    }

    /**
     * Sets the the presettle all sends option.  When true all MessageProducers that
     * are created will send their messages as settled.
     *
     * @param presettleProducers
     *      the presettleProducers value to apply.
     */
    public void setPresettleProducers(boolean presettleProducers) {
        this.presettleProducers = presettleProducers;
    }

    /**
     * @return the presettleTopicProducers setting for this policy
     */
    public boolean isPresettleTopicProducers() {
        return presettleTopicProducers;
    }

    /**
     * Sets the presettle Topic sends option.  When true any MessageProducer that
     * is created that sends to a Topic will send its messages presettled, and any
     * anonymous MessageProducer will send Messages that are sent to a Topic as
     * presettled as well.
     *
     * @param presettleTopicProducers
     *      the presettleTopicProducers value to apply.
     */
    public void setPresettleTopicProducers(boolean presettleTopicProducers) {
        this.presettleTopicProducers = presettleTopicProducers;
    }

    /**
     * @return the presettleQueueSends setting for this policy
     */
    public boolean isPresettleQueueProducers() {
        return presettleQueueProducers;
    }

    /**
     * Sets the presettle Queue sends option.  When true any MessageProducer that
     * is created that sends to a Queue will send its messages presettled, and any
     * anonymous MessageProducer will send Messages that are sent to a Queue as
     * presettled as well.
     *
     * @param presettleQueueProducers
     *      the presettleQueueSends value to apply.
     */
    public void setPresettleQueueProducers(boolean presettleQueueProducers) {
        this.presettleQueueProducers = presettleQueueProducers;
    }

    /**
     * @return the presettleTransactedSends setting for this policy
     */
    public boolean isPresettleTransactedProducers() {
        return presettleTransactedProducers;
    }

    /**
     * Sets the presettle in transactions option.  When true any MessageProducer that is
     * operating inside of a transacted session will send its messages presettled.
     *
     * @param presettleTransactedProducers the presettleTransactedSends to set
     */
    public void setPresettleTransactedProducers(boolean presettleTransactedProducers) {
        this.presettleTransactedProducers = presettleTransactedProducers;
    }

    /**
     * Determines when a producer will send message presettled.
     * <p>
     * Called when the a producer is being created to determine whether the producer will
     * be configured to send all its message as presettled or not.
     * <p>
     * For an anonymous producer this method is called on each send to allow the policy to
     * be applied to the target destination that the message will be sent to.
     *
     * @param destination
     *      the destination that the producer will be sending to.
     * @param session
     *      the session that owns the producer.
     *
     * @return true if the producer should send presettled.
     */
    public boolean isProducerPresttled(JmsDestination destination, JmsSession session) {

        if (presettleAll || presettleProducers) {
            return true;
        } else if (session.isTransacted() && presettleTransactedProducers) {
            return true;
        } else if (destination != null && destination.isQueue() && presettleQueueProducers) {
            return true;
        } else if (destination != null && destination.isTopic() && presettleTopicProducers) {
            return true;
        }

        return false;
    }

    /**
     * @return the presettleConsumers configuration value for this policy.
     */
    public boolean isPresettleConsumers() {
        return presettleConsumers;
    }

    /**
     * The presettle all consumers value to apply.  When true all MessageConsumer
     * instances created will indicate that presettled messages are requested.
     *
     * @param presettleConsumers
     *      the presettleConsumers value to apply to this policy.
     */
    public void setPresettleConsumers(boolean presettleConsumers) {
        this.presettleConsumers = presettleConsumers;
    }

    /**
     * @return the presettleTopicConsumers setting for this policy.
     */
    public boolean isPresettleTopicConsumers() {
        return presettleTopicConsumers;
    }

    /**
     * The presettle Topic consumers value to apply.  When true any MessageConsumer for
     * a Topic destination will indicate that presettled messages are requested.
     *
     * @param presettleTopicConsumers
     *      the presettleTopicConsumers value to apply to this policy.
     */
    public void setPresettleTopicConsumers(boolean presettleTopicConsumers) {
        this.presettleTopicConsumers = presettleTopicConsumers;
    }

    /**
     * @return the presettleQueueConsumers setting for this policy.
     */
    public boolean isPresettleQueueConsumers() {
        return presettleQueueConsumers;
    }

    /**
     * The presettle Queue consumers value to apply.  When true any MessageConsumer for
     * a Queue destination will indicate that presettled messages are requested.
     *
     * @param presettleQueueConsumers
     *      the presettleQueueConsumers value to apply to this policy.
     */
    public void setPresettleQueueConsumers(boolean presettleQueueConsumers) {
        this.presettleQueueConsumers = presettleQueueConsumers;
    }

    /**
     * Determines when a consumer will be created with the settlement mode set to presettled.
     * <p>
     * Called when the a consumer is being created to determine whether the consumer will
     * be configured to request that the remote sends it message that are presettled.
     * <p>
     *
     * @param destination
     *      the destination that the consumer will be listening to.
     * @param session
     *      the session that owns the consumer being created.
     *
     * @return true if the producer should send presettled.
     */
    public boolean isConsumerPresttled(JmsDestination destination, JmsSession session) {

        if (session.isTransacted()) {
            return false;
        } else if (presettleAll || presettleConsumers) {
            return true;
        } else if (destination != null && destination.isQueue() && presettleQueueConsumers) {
            return true;
        } else if (destination != null && destination.isTopic() && presettleTopicConsumers) {
            return true;
        }

        return false;
    }
}