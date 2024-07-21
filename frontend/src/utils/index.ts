export const getTokenFromCookie = () => {
  if (typeof document !== "undefined") {
    const match = document.cookie.match(new RegExp("(^| )user=([^;]+)"));
    if (match) return match[2];
  }
  return null;
};
