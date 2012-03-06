package com.zachklipp.wispr_android;

import java.util.ArrayList;

import android.content.Context;

// See http://erratasec.blogspot.com/2010/09/apples-secret-wispr-request.html
public abstract class CaptivePortalDetector
{
  // Set by checkForCaptivePortal().
  private CaptivePortalInfo mPortal;
  private ArrayList<CaptivePortalHandler> mHandlers = new ArrayList<CaptivePortalHandler>();
  
  // Should eventually cause reportCaptivePortal() to be called, followed
  // by triggerHandlers().
  public abstract void checkForCaptivePortal(Context context);
  
  public boolean isOnCaptivePortal()
  {
    return (mPortal != null);
  }
  
  public CaptivePortalInfo getCaptivePortal()
  {
    return mPortal;
  }
  
  public void addCaptivePortalHandler(CaptivePortalHandler handler)
  {
    if (handler != null)
    {
      mHandlers.add(handler);
    }
  }
  
  protected void reportCaptivePortal(Context context, CaptivePortalInfo portal)
  {
    mPortal = portal;
    triggerHandlers(context);
  }
  
  private void triggerHandlers(Context context)
  {
    for (CaptivePortalHandler handler : mHandlers)
    {
      handler.onCaptivePortalDetected(context, mPortal);
    }
  }
}