package com.flageolett.nodeconfig.Notifications;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

public class Library
{
    public static void send(Project project)
    {
        NotificationGroup group = NotificationGroup.create(
            "NodeConfig",
            NotificationDisplayType.BALLOON,
            true,
            null,
                null,
                null,
                null
        );

        String spaces = StringUtils.repeat("&nbsp;", 16);

        Notification notification = group.createNotification(
            "Enable node-config completions?",
            "",
            "<a href='1'>Yes</a>" + spaces + "<a href='0'>No</a>",
            NotificationType.INFORMATION,
            new Listener(project)
        );

        Notifications.Bus.notify(notification);
    }
}
