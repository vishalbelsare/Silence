package org.whispersystems.textsecure.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;

public class Util {
  public static boolean isEmpty(String value) {
    return value == null || value.trim().length() == 0;
  }

  public static boolean isEmpty(EditText value) {
    return value == null || value.getText() == null || isEmpty(value.getText().toString());
  }

  public static boolean isEmpty(CharSequence value) {
    return value == null || value.length() == 0;
  }

  public static void showAlertDialog(Context context, String title, String message) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle(title);
    dialog.setMessage(message);
    dialog.setIcon(android.R.drawable.ic_dialog_alert);
    dialog.setPositiveButton(android.R.string.ok, null);
    dialog.show();
  }

  public static String getSecret(int size) {
    try {
      byte[] secret = new byte[size];
      SecureRandom.getInstance("SHA1PRNG").nextBytes(secret);
      return Base64.encodeBytes(secret);
    } catch (NoSuchAlgorithmException nsae) {
      throw new AssertionError(nsae);
    }
  }

  public static String readFully(File file) throws IOException {
    return readFully(new FileInputStream(file));
  }

  public static String readFully(InputStream in) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    byte[] buffer              = new byte[4096];
    int read;

    while ((read = in.read(buffer)) != -1) {
      bout.write(buffer, 0, read);
    }

    in.close();

    return new String(bout.toByteArray());
  }

  public static void copy(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[4096];
    int read;

    while ((read = in.read(buffer)) != -1) {
      out.write(buffer, 0, read);
    }

    in.close();
    out.close();
  }

  public static String join(Collection<String> list, String delimiter) {
    StringBuilder result = new StringBuilder();
    int i=0;

    for (String item : list) {
      result.append(item);

      if (++i < list.size())
        result.append(delimiter);
    }

    return result.toString();
  }
}