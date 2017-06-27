package com.eutechpro.smshelp


class TestingApplication : SmsHelpApplication() {
    /**
     * Just to disable stetho while we are running UnitTests
     */
    override fun needStetho(): Boolean {
        return false
    }
}