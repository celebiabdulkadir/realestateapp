import { notification } from "antd";
import React, { useEffect } from "react";

interface ToastNotificationProps {
  open: boolean;
  message: string;
  description: string;
  severity: "success" | "info" | "warning" | "error";
}

const ToastNotification: React.FC<ToastNotificationProps> = React.memo(
  (props) => {
    const [api, contextHolder] = notification.useNotification();

    useEffect(() => {
      if (props.open) {
        api[props.severity]({
          message: props.message,
          description: (
            <pre style={{ whiteSpace: "pre-wrap" }}>{props.description}</pre>
          ),
          placement: "topRight",
          duration: 3,
        });
      }
    }, [props.open, props.message, props.description, props.severity, api]);

    return <>{contextHolder}</>;
  }
);

ToastNotification.displayName = "ToastNotification";
export { ToastNotification };
